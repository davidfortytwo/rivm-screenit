package nl.rivm.screenit.main.model.mamma.beoordeling;

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

import nl.rivm.screenit.model.InstellingGebruiker;
import nl.rivm.screenit.model.enums.MammaConclusieReviewFilterOptie;
import nl.topicuszorg.wicket.hibernate.util.ModelUtil;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public class MammaConclusieReviewZoekObject implements IDetachable
{
    private IModel<InstellingGebruiker> instellingGebruiker;

    private MammaConclusieReviewFilterOptie filterOptie = MammaConclusieReviewFilterOptie.ALLES;

    private Boolean gezienTonen = false;

    private Boolean voorDashboard = false;

    public InstellingGebruiker getInstellingGebruiker()
    {
        return ModelUtil.nullSafeGet(instellingGebruiker);
    }

    public void setInstellingGebruiker(InstellingGebruiker instellingGebruiker)
    {
        this.instellingGebruiker = ModelUtil.sModel(instellingGebruiker);
    }

    public MammaConclusieReviewFilterOptie getFilterOptie()
    {
        return filterOptie;
    }

    public void setFilterOptie(MammaConclusieReviewFilterOptie filterOptie)
    {
        this.filterOptie = filterOptie;
    }

    public Boolean getGezienTonen()
    {
        return gezienTonen;
    }

    public void setGezienTonen(boolean gezienTonen)
    {
        this.gezienTonen = gezienTonen;
    }

    public Boolean getVoorDashboard()
    {
        return voorDashboard;
    }

    public void setVoorDashboard(Boolean voorDashboard)
    {
        this.voorDashboard = voorDashboard;
    }

    @Override public void detach()
    {
        ModelUtil.nullSafeDetach(instellingGebruiker);
    }
}
