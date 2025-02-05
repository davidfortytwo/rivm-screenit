package nl.rivm.screenit.main.web.gebruiker.algemeen.projecten.brieven;

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

import java.util.ArrayList;
import java.util.List;

import nl.rivm.screenit.main.web.component.BriefTypeChoiceRenderer;
import nl.rivm.screenit.main.web.component.dropdown.ScreenitDropdown;
import nl.rivm.screenit.model.enums.BriefType;
import nl.rivm.screenit.model.project.ProjectBriefActie;
import nl.rivm.screenit.model.project.ProjectBriefActieType;
import nl.rivm.screenit.util.ProjectUtil;

import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

public class BriefActieTypeXmetYPanel extends GenericPanel<ProjectBriefActie>
{

	private static final long serialVersionUID = 1L;

	public BriefActieTypeXmetYPanel(String id, IModel<ProjectBriefActie> model)
	{
		super(id, model);

		List<ProjectBriefActieType> types = new ArrayList<ProjectBriefActieType>();
		types.add(ProjectBriefActieType.XDAGENNAY);

		ScreenitDropdown<BriefType> moment = new ScreenitDropdown<BriefType>("briefType", new ListModel<BriefType>(ProjectUtil.getBriefTypesWithVervanging(types)),
			new BriefTypeChoiceRenderer());
		moment.setRequired(true);
		moment.setLabel(Model.of("Brieftype"));
		add(moment);
	}
}
