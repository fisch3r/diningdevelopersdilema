package org.diningdevelopers.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.diningdevelopers.model.DecisionModel;
import org.diningdevelopers.model.DecisionTable;
import org.diningdevelopers.model.ResultModel;
import org.diningdevelopers.service.DecisionService;
import org.diningdevelopers.service.EventService;
import org.diningdevelopers.utils.FacesUtils;
import org.primefaces.model.chart.PieChartModel;

@Named
@SessionScoped
public class VotingsOverview implements Serializable {

	@Inject
	private DecisionService decisionService;

	@Inject
	private EventService eventService;

	private DecisionTable decisionTable;

	private PieChartModel pieModel;

	public DecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void init(ComponentSystemEvent event) {
		if (FacesUtils.isNotPostback()) {
			update();
		}
	}

	public void setDecisionTable(DecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public String update() {
		decisionTable = decisionService.buildDecisionTable(null);
		updatePieModel(decisionTable);
		return null;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	private void updatePieModel(DecisionTable decisionTable) {
		pieModel = new PieChartModel();
		for (DecisionModel d : decisionTable.getDecisions()) {
			pieModel.getData().put(d.getLocationName(), d.getPointsTotal());
		}
	} 

	public String getVotingState() {
		return eventService.getLatestEventState();
	}

	public boolean isVotingClosed() {
		return eventService.isLatestEventClosed();
	}

	public ResultModel getResultModel() {
		return decisionService.getResultModelForLatestVote();
	}

}
