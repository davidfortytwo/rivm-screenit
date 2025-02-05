
package nl.rivm.screenit.main.web.gebruiker.algemeen.batch;

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

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.UncategorizedJmsException;

public class BatchStatusPage extends BatchBasePage
{

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(BatchStatusPage.class);

	public BatchStatusPage()
	{

		try
		{
			add(new AjaxLazyLoadPanel("statusPanel")
			{

				private static final long serialVersionUID = 1L;

				@Override
				public Component getLazyLoadComponent(String markupId)
				{
					return new BatchStatusPanel(markupId);
				}
			});

		}
		catch (UncategorizedJmsException e)
		{
			LOG.error("Er is een mogelijk probleem met ActiveMq, waarschuw een beheerder", e);
			error("Er is een mogelijk probleem met ActiveMq, waarschuw een beheerder");
		}
	}

}
