package nl.rivm.screenit.dao.mamma.impl;

/*-
 * ========================LICENSE_START=================================
 * screenit-base
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

import nl.rivm.screenit.dao.mamma.MammaSelectieRestrictions;
import nl.rivm.screenit.model.DossierStatus;
import nl.rivm.screenit.model.enums.Deelnamemodus;
import nl.rivm.screenit.util.query.ScreenitRestrictions;
import nl.topicuszorg.hibernate.restrictions.NvlRestrictions;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MammaSelectieRestrictionsImpl implements MammaSelectieRestrictions
{

	@Override
	public void addStandaardSelectieRestricties(Criteria criteria)
	{
		criteria.add(Restrictions.ne("dossier.deelnamemodus", Deelnamemodus.SELECTIEBLOKKADE));

		criteria.add(NvlRestrictions.eq("dossier.status", DossierStatus.ACTIEF, "'" + DossierStatus.ACTIEF + "'"));
		criteria.add(Restrictions.or(Restrictions.isNotNull("adres.postcode"), Restrictions.isNotNull("tijdelijkGbaAdres.postcode")));
		ScreenitRestrictions.addClientBaseRestrictions(criteria, "client", "persoon");
	}
}
