package nl.rivm.screenit.batch.jobs.helpers;

/*-
 * ========================LICENSE_START=================================
 * screenit-batch-base
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

import org.hibernate.ScrollableResults;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public abstract class BaseScrollableResultReader extends BaseTypedScrollableResultReader<Long>
{
	@Override
	public Long read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException
	{
		ScrollableResults scrollableResults = resultSet.get();
		while (scrollableResults.next())
		{
			Long id = getScrollableResult(scrollableResults);

			if (!processedIds.contains(id))
			{
				processedIds.add(id);
				return id;
			}
		}
		return null;
	}

	protected Long getScrollableResult(ScrollableResults scrollableResults)
	{
		return scrollableResults.getLong(0);
	}
}
