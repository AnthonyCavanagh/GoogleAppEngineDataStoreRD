package com.cav.googlecloud.service.datastore;

import com.cav.googlecloud.model.data.AddFields;
import com.cav.googlecloud.model.data.AddState;
import com.cav.googlecloud.model.data.Project;
import com.cav.googlecloud.model.data.State;
import com.cav.googlecloud.model.data.ProjectKey;
import com.cav.googlecloud.model.data.Type;
import com.google.appengine.api.datastore.Key;

public interface DataStorageService {
	
	Type addType(Type type);
	State addState(State state);
	Type findType(Long id);
	Key findTypeKey(Long id);
	Key findTypeSateKey(Long id);
	Project addProject(Project project);
	AddState addState(AddState state);
	Key findProjectKey(Long id);
	Key findStateKey(Long id);
	Key findStateKey(ProjectKey key);
	Project findProject(ProjectKey key);
	
	String createFieldsProject(AddFields fields);
	String createFieldsType(AddFields fields);
	
	String findFieldsProject(Long id);
	String findFieldsType(Long id);
}
