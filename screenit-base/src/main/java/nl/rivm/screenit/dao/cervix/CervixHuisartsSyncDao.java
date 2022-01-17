package nl.rivm.screenit.dao.cervix;

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

import nl.rivm.screenit.model.InstellingGebruiker;
import nl.rivm.screenit.model.cervix.CervixHuisarts;
import nl.rivm.screenit.model.cervix.CervixHuisartsAdres;
import nl.rivm.screenit.model.cervix.CervixHuisartsLocatie;
import nl.rivm.screenit.model.cervix.CervixLabformulierAanvraag;

public interface CervixHuisartsSyncDao
{
	CervixHuisartsLocatie locatieFindByHuisartsportaalId(Long id);

	CervixHuisartsAdres adresFindByHuisartsportaalId(Long id);

	CervixHuisarts huisartsFindByHuisartsportaalId(Long id);

	InstellingGebruiker findInstellingGebruikerByName(String naam, Long id);

	CervixLabformulierAanvraag aanvraagFindByHuisartsportaalId(Long id);

}
