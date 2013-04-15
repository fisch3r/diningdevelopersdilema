package org.diningdevelopers.core.database.gateways;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.diningdevelopers.core.business.persistence.AuditPersistence;
import org.diningdevelopers.core.database.dao.AuditDao;
import org.diningdevelopers.core.database.entities.Audit;
import org.diningdevelopers.dao.EventDao;
import org.diningdevelopers.model.AuditModel;
import org.diningdevelopers.service.MappingService;

@Stateless
public class AuditService implements AuditPersistence {

	@Inject
	private AuditDao auditDao;

	@Inject
	private EventDao eventDao;

	@Inject
	private MappingService mappingService;

	@Override
	public void createAudit(String username, String message) {
		Audit audit = new Audit();
		audit.setDate(new Date());
		audit.setMessage(message);
		audit.setUsername(username);
		auditDao.save(audit);
	}


	@Override
	public List<AuditModel> findLatest(int maxResult) {
		Date filterDate = eventDao.findLatestActiveVoting();
		List<Audit> audits = auditDao.findLatest(maxResult, filterDate);

		return mappingService.mapCollection(audits, AuditModel.class);
	}

}