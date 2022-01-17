package nl.rivm.screenit.huisartsenportaal.service;

/*-
 * ========================LICENSE_START=================================
 * screenit-huisartsenportaal
 * %%
 * Copyright (C) 2016 - 2022 Facilitaire Samenwerking Bevolkingsonderzoek
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

import nl.rivm.screenit.huisartsenportaal.dto.BetalingCsvDto;
import nl.rivm.screenit.huisartsenportaal.dto.BetalingDto;
import nl.rivm.screenit.huisartsenportaal.dto.BetalingZoekObjectDto;
import nl.rivm.screenit.huisartsenportaal.dto.BetalingenTotalenDto;
import nl.rivm.screenit.huisartsenportaal.dto.VerrichtingDto;
import nl.rivm.screenit.huisartsenportaal.model.Betaling;
import nl.rivm.screenit.huisartsenportaal.model.Huisarts;
import nl.rivm.screenit.huisartsenportaal.model.Verrichting;

public interface BetalingService
{

	BetalingenTotalenDto getBetalingen(Huisarts huisarts, BetalingZoekObjectDto betalingDto);

	List<BetalingCsvDto> getBetalingenCsv(Huisarts huisarts, BetalingZoekObjectDto betalingDto);

	Betaling convertFromDto(BetalingDto betalingDto);

	BetalingDto convertToDto(Betaling betaling, VerrichtingDto verrichtingDto);
}
