package nl.rivm.screenit.main.web.component;

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

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

public class ScreenitWachtwoordField extends GenericPanel<String>
{

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(new OnDomReadyHeaderItem("initToonWachtwoordToggle()"));
	}

	public ScreenitWachtwoordField(String id, IModel<String> model, boolean required, IValidator<String> validator)
	{
		super(id, model);
		PasswordTextField inputField = new PasswordTextField("wachtwoord", getModel());
		inputField.setOutputMarkupId(true);
		inputField.setRequired(required);
		ComponentHelper.setAutocompleteOff(inputField);

		if (validator != null)
		{
			inputField.add(validator);
		}

		add(inputField);
	}
}
