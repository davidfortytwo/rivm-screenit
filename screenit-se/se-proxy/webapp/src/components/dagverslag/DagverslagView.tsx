import React from "react"
import DatumkiezerContainer from "../daglijst/DatumkiezerContainer"
import DagproductieContainer from "./DagproductieContainer"
import DagStatistiekenContainer from "./DagStatistiekenContainer"
import DagSynchronisatieContainer from "./DagSynchronisatieContainer"
import DagAfrondstatusContainer from "./DagAfrondstatusContainer"
import {Button} from "reactstrap"

export type DagverslagViewStateProps = {
	afsprakenDoorvoerenDisabled: boolean;
	doorvoerenFeedback: string;
};

export type DagverslagViewDispatchProps = {
	showAfsprakenDoorvoerenPopup: (afsprakenDoorvoerenDisabled: boolean, doorvoerenFeedback: string) => void;
}

export default class DagverslagView extends React.Component<DagverslagViewStateProps & DagverslagViewDispatchProps> {
	render(): JSX.Element {
		const clickAfsprakenDoorvoeren = (): void => this.props.showAfsprakenDoorvoerenPopup(this.props.afsprakenDoorvoerenDisabled, this.props.doorvoerenFeedback)

		return <div className="dagverslag-lijst">
			<div className="row row-dagverslag-top">
				<div className="col-3">
					<Button
						className={this.props.afsprakenDoorvoerenDisabled ? "btn-primary-se disabled" : "btn-primary-se"}
						onClick={clickAfsprakenDoorvoeren}>
						Dag afsluiten
					</Button>
				</div>
				<div className="col-9">
					<div className="daglijst-blokken-rechts">
						<div className="dagverslag-datumkiezer">
							<DatumkiezerContainer/>
						</div>
					</div>
				</div>
			</div>
			<DagproductieContainer/>
			<div className="row row-no-gutters">
				<div className="col-6 col-no-gutters-left">
					<DagStatistiekenContainer/>
				</div>
				<div className="col-6 col-no-gutters-right">
					<DagSynchronisatieContainer/>
					<DagAfrondstatusContainer/>
				</div>
			</div>
		</div>
	}

}