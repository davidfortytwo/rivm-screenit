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
var laesieIconenOpgeslagenCallbacks = [];

function laesieIconenOpslaan(lezingId) {
    laesieIconenOpgeslagenCallbacks[lezingId](JSON.stringify(laesiesAlsLijst(lezingId)));
}

function laesiesAlsLijst(lezingId) {
    return laesiesVanElkType(laesies[lezingId].RECHTER_BORST).concat(laesiesVanElkType(laesies[lezingId].LINKER_BORST));
}

function nummerDeLaesies (laesies) {
	return laesies.map((laesie, index) => {
		return {
            ...laesie, ...{nummer: index + 1},
		};
	});
}

function laesiesVanElkType(laesiesVanBorst) {
    return nummerDeLaesies(laesiesVanBorst.ARCHITECTUURVERSTORING).
        concat(nummerDeLaesies(laesiesVanBorst.ASYMMETRIE), nummerDeLaesies(laesiesVanBorst.CALCIFICATIES), nummerDeLaesies(laesiesVanBorst.MASSA));
}
