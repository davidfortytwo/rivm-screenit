package nl.rivm.screenit.batch.jobs.generalis.brieven.algemeen.genererenstep;

/*-
 * ========================LICENSE_START=================================
 * screenit-batch-alg
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.rivm.screenit.batch.jobs.brieven.genereren.AbstractBrievenGenererenPartitioner;
import nl.rivm.screenit.model.ScreeningOrganisatie;
import nl.rivm.screenit.model.enums.BriefType;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class AlgemeneBrievenGenererenPartitioner extends AbstractBrievenGenererenPartitioner
{
	static final String KEY_BRIEFTYPE = "algemeen.brieftype";

	@Override
	protected void fillingData(Map<String, ExecutionContext> partities, ScreeningOrganisatie organisatie)
	{
		for (BriefType briefType : getBriefTypes())
		{
			ExecutionContext executionContext = new ExecutionContext();
			executionContext.put(KEY_BRIEFTYPE, briefType.name());
			partities.put(briefType.name(), executionContext);
		}
	}

	private List<BriefType> getBriefTypes()
	{
		List<BriefType> briefTypes = new ArrayList<>();
		briefTypes.add(BriefType.CLIENT_INZAGE_PERSOONSGEGEVENS_AANVRAAG);
		briefTypes.add(BriefType.CLIENT_INZAGE_PERSOONSGEGEVENS_HANDTEKENING);
		briefTypes.add(BriefType.CLIENT_SIGNALERING_GENDER);
		return briefTypes;
	}
}
