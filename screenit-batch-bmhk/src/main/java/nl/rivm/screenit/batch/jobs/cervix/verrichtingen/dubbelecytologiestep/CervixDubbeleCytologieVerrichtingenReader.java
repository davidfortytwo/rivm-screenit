package nl.rivm.screenit.batch.jobs.cervix.verrichtingen.dubbelecytologiestep;

/*-
 * ========================LICENSE_START=================================
 * screenit-batch-bmhk
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

import nl.rivm.screenit.batch.jobs.helpers.BaseScrollableResultReader;
import nl.rivm.screenit.model.cervix.CervixUitstrijkje;
import nl.rivm.screenit.model.cervix.enums.CervixTariefType;
import nl.rivm.screenit.model.cervix.facturatie.CervixVerrichting;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Component;

import static nl.rivm.screenit.batch.jobs.cervix.verrichtingen.CervixBepalenVerrichtingenJobConfiguration.CERVIX_BEPALEN_VERRICHTINGEN_JOB_FETCH_SIZE;

@Component
public class CervixDubbeleCytologieVerrichtingenReader extends BaseScrollableResultReader
{

	public CervixDubbeleCytologieVerrichtingenReader()
	{
		super.setFetchSize(CERVIX_BEPALEN_VERRICHTINGEN_JOB_FETCH_SIZE);
	}

	@Override
	public Criteria createCriteria(StatelessSession session) throws HibernateException
	{
		var criteria = session.createCriteria(CervixUitstrijkje.class);
		criteria.createAlias("verrichtingen", "verrichtingen");

		criteria.add(Restrictions.isNotNull("cytologieVerslag"));

		var verrichtingSubquery = DetachedCriteria.forClass(CervixVerrichting.class);
		verrichtingSubquery.add(Restrictions.in("type", new CervixTariefType[] { CervixTariefType.LAB_CYTOLOGIE_NA_HPV_ZAS,
			CervixTariefType.LAB_CYTOLOGIE_NA_HPV_UITSTRIJKJE, CervixTariefType.LAB_CYTOLOGIE_VERVOLGUITSTRIJKJE }));
		verrichtingSubquery.setProjection(Projections.property("monster"));

		criteria.add(Subqueries.propertyNotIn("verrichtingen.monster", verrichtingSubquery));
		criteria.add(Restrictions.isNotEmpty("verrichtingen"));
		return criteria;
	}
}
