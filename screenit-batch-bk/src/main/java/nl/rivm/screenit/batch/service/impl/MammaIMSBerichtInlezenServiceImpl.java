package nl.rivm.screenit.batch.service.impl;

/*-
 * ========================LICENSE_START=================================
 * screenit-batch-bk
 * %%
 * Copyright (C) 2012 - 2020 Facilitaire Samenwerking Bevolkingsonderzoek
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * =========================LICENSE_END==================================
 */

import java.util.List;

import nl.rivm.screenit.batch.service.MammaCStoreService;
import nl.rivm.screenit.batch.service.MammaIMSBerichtInlezenService;
import nl.rivm.screenit.dao.mamma.MammaIMSDao;
import nl.rivm.screenit.model.Client;
import nl.rivm.screenit.model.berichten.enums.BerichtStatus;
import nl.rivm.screenit.model.enums.Bevolkingsonderzoek;
import nl.rivm.screenit.model.enums.Level;
import nl.rivm.screenit.model.enums.LogGebeurtenis;
import nl.rivm.screenit.model.logging.MammaHl7v24BerichtFoutenLogEvent;
import nl.rivm.screenit.model.mamma.MammaScreeningRonde;
import nl.rivm.screenit.model.mamma.MammaUploadBeeldenPoging;
import nl.rivm.screenit.model.mamma.berichten.MammaIMSBericht;
import nl.rivm.screenit.service.ClientService;
import nl.rivm.screenit.service.LogService;
import nl.rivm.screenit.service.mamma.MammaBaseOnderzoekService;
import nl.rivm.screenit.service.mamma.MammaBaseScreeningrondeService;
import nl.topicuszorg.hibernate.spring.dao.HibernateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.uhn.hl7v2.HL7Exception;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MammaIMSBerichtInlezenServiceImpl implements MammaIMSBerichtInlezenService
{
	private static final Logger LOG = LoggerFactory.getLogger(MammaIMSBerichtInlezenServiceImpl.class);

	@Autowired
	private MammaIMSDao mammaIMSDao;

	@Autowired
	private HibernateService hibernateService;

	@Autowired
	private LogService logService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private MammaBaseOnderzoekService onderzoekService;

	@Autowired
	private MammaBaseScreeningrondeService screeningrondeService;

	@Autowired
	private MammaCStoreService uploadBeeldenService;

	@Override
	public List<MammaIMSBericht> getAlleNietVerwerkteIMSBerichten()
	{
		return mammaIMSDao.getAlleNietVerwerkteIMSBerichten();
	}

	@Override
	public void verwerkBericht(MammaIMSBericht bericht)
	{
		Client client = clientService.getClientByBsn(bericht.getBsn());
		if (client != null)
		{
			try
			{
				switch (bericht.getOrmStatus())
				{
				case CENTRALAVAILABLE:
					verwerkBerichtBeeldenBeschikbaar(bericht, client);
					break;
				case DELETED:
					verwerkBerichtBeeldenVerwijderd(bericht, client);
					break;
				case ERROR:
					verwerkBerichtBeeldenVerwijderenError(bericht, client);
					break;
				default:
					String melding = String.format("ORM status (%s) van ontvangen IMS bericht (%s) wordt niet geaccepteerd.", bericht.getOrmStatus().name(),
						bericht.getMessageId());
					markeerBerichtAlsFout(bericht, melding);
				}
				bericht.setBerichtStatus(BerichtStatus.VERWERKT);
				hibernateService.saveOrUpdate(bericht);
			}
			catch (HL7Exception e)
			{
				markeerBerichtAlsFout(bericht, e.getMessage());
			}
		}
		else
		{
			String melding = String.format("Ontvangen IMS bericht (%s) kan niet gekoppeld worden.", bericht.getMessageId());
			markeerBerichtAlsFout(bericht, melding);
		}
	}

	private void verwerkBerichtBeeldenBeschikbaar(MammaIMSBericht bericht, Client client) throws HL7Exception
	{
		MammaScreeningRonde ronde = screeningrondeService.getRondeVanUitnodigingsr(client, bericht.getAccessionNumber());
		if (ronde != null)
		{
			onderzoekService.ontvangBeeldenVoorOnderzoek(client, ronde);
			LOG.info(String.format("Beelden beschikbaar voor accession number: %s, client id: %d", bericht.getAccessionNumber(), client.getId()));
		}
		else
		{
			MammaUploadBeeldenPoging uploadBeeldenPoging = uploadBeeldenService.getUploadPoging(bericht.getAccessionNumber());
			if (uploadBeeldenPoging == null)
			{
				throw new HL7Exception(
					String.format("Geen screeningsronde of uploadverzoek gevonden voor client met id %s met accessionNumber %s", client.getId(), bericht.getAccessionNumber()));
			}
			uploadBeeldenService.beeldenOntvangenUploadPoging(uploadBeeldenPoging);
		}
	}

	private void verwerkBerichtBeeldenVerwijderd(MammaIMSBericht bericht, Client client)
	{
		MammaUploadBeeldenPoging uploadBeeldenPoging = uploadBeeldenService.getUploadPoging(bericht.getAccessionNumber());
		if (uploadBeeldenPoging != null)
		{
			uploadBeeldenService.beeldenVerwijderdUploadVerzoek(uploadBeeldenPoging, bericht, client, false);
			LOG.info(String.format("Beelden verwijderd voor uploadverzoek met accession number: %s, client id: %d", bericht.getAccessionNumber(), client.getId()));
		}
		else
		{
			onderzoekService.beeldenVerwijderdVoorOnderzoek(bericht, client, false);
			LOG.info(String.format("Beelden verwijderd voor accession number: %s, client id: %d", bericht.getAccessionNumber(), client.getId()));
		}
	}

	private void verwerkBerichtBeeldenVerwijderenError(MammaIMSBericht bericht, Client client)
	{
		MammaUploadBeeldenPoging uploadBeeldenPoging = uploadBeeldenService.getUploadPoging(bericht.getAccessionNumber());
		if (uploadBeeldenPoging != null)
		{
			uploadBeeldenService.beeldenVerwijderdUploadVerzoek(uploadBeeldenPoging, bericht, client, true);
			LOG.info(String.format("Error tijdens verwijderen beelden uploadverzoek met accession number: %s, client id: %d", bericht.getAccessionNumber(), client.getId()));
		}
		else
		{
			onderzoekService.beeldenVerwijderdVoorOnderzoek(bericht, client, true);
			LOG.info(String.format("Error tijdens verwijderen beelden voor accession number: %s, client id: %d", bericht.getAccessionNumber(), client.getId()));
		}
	}

	@Override
	public void markeerBerichtAlsFout(MammaIMSBericht bericht, String melding)
	{
		bericht.setBerichtStatus(BerichtStatus.FOUT);
		hibernateService.saveOrUpdate(bericht);
		melding = String.format("%s, bericht: %s", melding, bericht.getHl7Bericht());

		MammaHl7v24BerichtFoutenLogEvent logEvent = new MammaHl7v24BerichtFoutenLogEvent();
		logEvent.setMelding(melding);
		logEvent.setHl7MessageStructure(bericht.getHl7Bericht());
		logEvent.setLevel(Level.WARNING);
		logEvent.setClient(null);

		logService.logGebeurtenis(LogGebeurtenis.MAMMA_HL7_BERICHT_ONTVANGEN_MISLUKT,
			logEvent,
			Bevolkingsonderzoek.MAMMA);
	}
}
