package nl.rivm.screenit.batch.dao.impl;

/*-
 * ========================LICENSE_START=================================
 * screenit-batch-bk
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

import nl.rivm.screenit.batch.dao.MammaIlmDao;
import nl.rivm.screenit.model.logging.MammaIlmLogEvent;
import nl.rivm.screenit.model.verwerkingverslag.mamma.MammaIlmBeeldenStatusRapportage;
import nl.topicuszorg.hibernate.spring.dao.impl.AbstractAutowiredDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public class MammaIlmDaoImpl extends AbstractAutowiredDao implements MammaIlmDao
{
	@Override
	public MammaIlmBeeldenStatusRapportage getMeestRecenteStatusRapportage()
	{
		Criteria crit = getSession().createCriteria(MammaIlmLogEvent.class);
		crit.createAlias("logRegel", "logRegel");
		crit.addOrder(Order.desc("logRegel.gebeurtenisDatum"));
		crit.setFirstResult(0);
		crit.setMaxResults(1);
		crit.setProjection(Projections.property("rapportage"));
		return (MammaIlmBeeldenStatusRapportage) crit.uniqueResult();
	}
}
