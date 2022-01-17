/*-
 * ========================LICENSE_START=================================
 * screenit-clientportaal
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
import React from "react"
import ActieBasePage from "../ActieBasePage"
import * as Yup from "yup"
import {saveTelefoonNummers} from "../../api/TelefoonnummerWijzigenThunkAction"
import {useThunkDispatch} from "../../index"
import {getString} from "../../utils/TekstPropertyUtil"
import {useSelector} from "react-redux"
import {State} from "../../datatypes/State"
import {navigateAndShowToast} from "../../utils/NavigationUtil"
import properties from "./TelefoonnummerWijzigenPage.json"
import {Formik} from "formik"
import SubmitForm from "../../components/form/SubmitForm"
import ScreenitTextfield from "../../components/input/ScreenitTextfield"
import {isTelefoonnummerValid} from "../../validators/TelefoonnummerValidator"

const TelefoonnummerWijzigenPage = () => {
    const dispatch = useThunkDispatch()
    const persoon = useSelector((state: State) => state.client.persoon)

    const initialValues = {
        telefoonnummer1: persoon.telefoonnummer1 || "",
        telefoonnummer2: persoon.telefoonnummer2 || "",
    }

    const validatieSchema: Yup.AnyObjectSchema = Yup.object().shape({
        telefoonnummer1: Yup.string()
            .test("telefoonnummerValidatie", getString(properties.form.invalid), function (value) {
                return isTelefoonnummerValid(value)
            }),
        telefoonnummer2: Yup.string()
            .test("telefoonnummerValidatie", getString(properties.form.invalid), function (value) {
                return isTelefoonnummerValid(value)
            }),
    })

    return (
        <ActieBasePage
            bvoName={""}
            title={getString(properties.page.title)}
            description={getString(properties.page.description)}
            hintBegin={getString(properties.page.hint)}>

            <Formik initialValues={initialValues}
                    validationSchema={validatieSchema}
                    onSubmit={(telefoonNummers) => {
                        dispatch(saveTelefoonNummers(telefoonNummers)).then(() => {
                            navigateAndShowToast("/profiel", getString(properties.toast.title), getString(properties.toast.description))
                        })
                    }}>

                {formikProps => (<SubmitForm title={getString(properties.form.title)}
                                             formikProps={formikProps}
                                             buttonLabel={getString(properties.form.button)}>

                    <ScreenitTextfield name={"telefoonnummer1"}
                                       value={formikProps.values.telefoonnummer1}
                                       invalidMessage={formikProps.errors.telefoonnummer1}
                                       placeholder={getString(properties.form.placeholder, ["1"])}
                                       onChange={value => formikProps.setFieldValue("telefoonnummer1", value)}/>

                    <ScreenitTextfield name={"telefoonnummer2"}
                                       value={formikProps.values.telefoonnummer2}
                                       invalidMessage={formikProps.errors.telefoonnummer2}
                                       placeholder={getString(properties.form.placeholder, ["2"])}
                                       onChange={value => formikProps.setFieldValue("telefoonnummer2", value)}/>

                </SubmitForm>)}

            </Formik>

        </ActieBasePage>
    )
}

export default TelefoonnummerWijzigenPage
