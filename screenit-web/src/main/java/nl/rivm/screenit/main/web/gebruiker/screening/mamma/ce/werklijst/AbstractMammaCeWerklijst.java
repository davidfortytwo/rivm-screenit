package nl.rivm.screenit.main.web.gebruiker.screening.mamma.ce.werklijst;

/*-
 * ========================LICENSE_START=================================
 * screenit-web
 * %%
 * Copyright (C) 2012 - 2022 Facilitaire Samenwerking Bevolkingsonderzoek
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

import nl.rivm.screenit.Constants;
import nl.rivm.screenit.main.model.mamma.beoordeling.MammaCeWerklijstZoekObject;
import nl.rivm.screenit.main.web.ScreenitSession;
import nl.rivm.screenit.main.web.component.table.ClientColumn;
import nl.rivm.screenit.main.web.component.table.EnumPropertyColumn;
import nl.rivm.screenit.main.web.component.table.GeboortedatumColumn;
import nl.rivm.screenit.main.web.gebruiker.screening.mamma.ce.AbstractMammaCePage;
import nl.rivm.screenit.model.mamma.MammaBeoordeling;
import nl.topicuszorg.wicket.hibernate.util.ModelUtil;
import nl.topicuszorg.wicket.search.column.DateTimePropertyColumn;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class AbstractMammaCeWerklijst extends AbstractMammaCePage
{

	protected IModel<MammaCeWerklijstZoekObject> zoekObjectModel;

	protected MammaCeVerwijsVerslagenDataProvider onderzoekDataProvider;

	protected final WebMarkupContainer resultatenContainer;

	public AbstractMammaCeWerklijst()
	{
		if (ScreenitSession.get().isZoekObjectGezetForComponent(getPageClass()))
		{
			zoekObjectModel = (IModel<MammaCeWerklijstZoekObject>) ScreenitSession.get().getZoekObject(getPageClass());
		}
		else
		{
			zoekObjectModel = new CompoundPropertyModel<>(new MammaCeWerklijstZoekObject());
		}
		onderzoekDataProvider = new MammaCeVerwijsVerslagenDataProvider("onderzoek.creatieDatum", zoekObjectModel);

		resultatenContainer = new WebMarkupContainer("resultatenContainer");
		resultatenContainer.setOutputMarkupId(Boolean.TRUE);
		add(resultatenContainer);
	}

	@Override
	protected void onDetach()
	{
		ModelUtil.nullSafeDetach(zoekObjectModel);
		super.onDetach();
	}

	protected IColumn<MammaBeoordeling, String> getOnderzoeksdatumColumn()
	{
		return new DateTimePropertyColumn<>(Model.of("Onderzoeksdatum SE"), "onderzoek.creatieDatum", "onderzoek.creatieDatum",
			Constants.getDateTimeFormat());
	}

	protected IColumn<MammaBeoordeling, String> getStatusColumn()
	{
		return new EnumPropertyColumn<>(Model.of("Status"), "beoordeling.status", "status", this);
	}

	protected IColumn<MammaBeoordeling, String> getBeColumn()
	{
		return new PropertyColumn<>(Model.of("BE"), "beoordelingsEenheid.naam", "beoordelingsEenheid.naam");
	}

	protected IColumn<MammaBeoordeling, String> getSeColumn()
	{
		return new PropertyColumn<>(Model.of("SE"), "se.code", "onderzoek.screeningsEenheid.code");
	}

	protected IColumn<MammaBeoordeling, String> getBsnColumn()
	{
		return new PropertyColumn<>(Model.of("BSN"), "persoon.bsn", "onderzoek.afspraak.uitnodiging.screeningRonde.dossier.client.persoon.bsn");
	}

	protected IColumn<MammaBeoordeling, String> getGeboortedatumColumn()
	{
		return new GeboortedatumColumn<>("persoon.geboortedatum", "onderzoek.afspraak.uitnodiging.screeningRonde.dossier.client.persoon");
	}

	protected IColumn<MammaBeoordeling, String> getClientColumn()
	{
		return new ClientColumn<>("persoon.achternaam", "onderzoek.afspraak.uitnodiging.screeningRonde.dossier.client");
	}

	protected IColumn<MammaBeoordeling, String> getVerslagdatumColumn()
	{
		return new DateTimePropertyColumn<>(Model.of("Verslagdatum"), "verslagLezing.beoordelingDatum", "verslagLezing.beoordelingDatum",
			Constants.getDateFormat());
	}
}
