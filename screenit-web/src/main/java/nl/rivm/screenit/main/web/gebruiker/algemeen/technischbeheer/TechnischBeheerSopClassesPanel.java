package nl.rivm.screenit.main.web.gebruiker.algemeen.technischbeheer;

/*-
 * ========================LICENSE_START=================================
 * screenit-web
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

import nl.rivm.screenit.main.model.Parameterisatie;
import nl.rivm.screenit.main.web.gebruiker.algemeen.parameterisatie.ParameterisatiePropertyModel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

public class TechnischBeheerSopClassesPanel extends BaseTechnischBeheerParametersPanel
{
	public TechnischBeheerSopClassesPanel(String id, IModel<Parameterisatie> model)
	{
		super(id, new ParameterisatiePropertyModel<>(model));
	}

	@Override
	protected Form<Parameterisatie> createAndGetForm()
	{
		Form<Parameterisatie> form = new Form<>("form");
		form.add(new TextArea<>("mammaDicomSopConfig"));
		return form;
	}

	@Override
	protected Component createAndGetOpslaanLink()
	{
		return new AjaxSubmitLink("opslaanSopConfig")
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				opslaan(target);
				info("Parameters zijn opgeslagen");
			}
		};
	}
}