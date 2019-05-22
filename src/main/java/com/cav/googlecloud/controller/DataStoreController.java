package com.cav.googlecloud.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;

import com.cav.googlecloud.model.data.AddFields;
import com.cav.googlecloud.model.data.AddState;
import com.cav.googlecloud.model.data.Field;
import com.cav.googlecloud.model.data.Find;
import com.cav.googlecloud.model.data.Project;
import com.cav.googlecloud.model.data.State;
import com.cav.googlecloud.model.data.ProjectKey;
import com.cav.googlecloud.model.data.Type;
import com.cav.googlecloud.service.datastore.DataStorageService;
import com.google.appengine.api.datastore.Key;

@RestController
public class DataStoreController {

	@Autowired
	private DataStorageService service;
	
	private static final Logger logger = LoggerFactory.getLogger(DataStoreController.class);
	
	
	@RequestMapping(value = "/saveTypeFields/{id}", method = RequestMethod.GET)
	public String saveTypeField(@PathVariable ("id")Long id) {
		AddFields fields = new AddFields();
		fields.setId(id);
		fields.setFields(createFields(10));
		return service.createFieldsType(fields);
	}
	
	@RequestMapping(value = "/findTypeFields/{id}", method = RequestMethod.GET)
	public String findTypeField(@PathVariable ("id")Long id) {
		return service.findFieldsType(id);
	}
	
	@RequestMapping(value = "/saveProjectFields/{id}", method = RequestMethod.GET)
	public String saveProjectField(@PathVariable ("id")Long id) {
		AddFields fields = new AddFields();
		fields.setId(id);
		fields.setFields(createFields(10));
		return service.createFieldsProject(fields);
	}
	
	@RequestMapping(value = "/findProjectFields/{id}", method = RequestMethod.GET)
	public String findProjectField(@PathVariable ("id")Long id) {
		return service.findFieldsProject(id);
	}
	
	
	
	@RequestMapping(value = "/createType", method = RequestMethod.POST)
	public ResponseEntity<Type>  saveType(@RequestBody Type type) {
		logger.info("Save type "+type.toString());
		type = service.addType(type);
		return new ResponseEntity<Type>(type, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findType", method = RequestMethod.POST)
	public ResponseEntity<Type>  fndType(@RequestBody Find find) {
		logger.info("Save type "+find.toString());
		Type type = service.findType(find.getId());
		return new ResponseEntity<Type>(type, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findTypeKey", method = RequestMethod.POST)
	public ResponseEntity<Key>  fndTypeKey(@RequestBody Find find) {
		logger.info("Save type "+find.toString());
		Key key = service.findTypeKey(find.getId());
		return new ResponseEntity<Key>(key, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findTypeStateKey", method = RequestMethod.POST)
	public ResponseEntity<Key>  fndTypeStateKey(@RequestBody Find find) {
		logger.info("Save type "+find.toString());
		Key key = service.findTypeSateKey(find.getId());
		return new ResponseEntity<Key>(key, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	public ResponseEntity<Project>  saveProject(@RequestBody Project project) {
		logger.info("Save project "+project.toString());
		project = service.addProject(project);
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findProject", method = RequestMethod.POST)
	public ResponseEntity<Project>  fndProject(@RequestBody ProjectKey key) {
		logger.info("Save type "+key.toString());
		Project project = service.findProject(key);
		return new ResponseEntity<Project>(project, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/AddState", method = RequestMethod.POST)
	public ResponseEntity<AddState>  saveState(@RequestBody AddState state) {
		logger.info("Save state "+state.toString());
		state = service.addState(state);
		return new ResponseEntity<AddState>(state, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findProjectKey", method = RequestMethod.POST)
	public ResponseEntity<Key>  fndProjectKey(@RequestBody Find find) {
		logger.info("Find Key for Project"+find.toString());
		Key key = service.findProjectKey(find.getId());
		return new ResponseEntity<Key>(key, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findStateKey1", method = RequestMethod.POST)
	public ResponseEntity<Key>  fndStateKey1(@RequestBody Find find) {
		logger.info("Find Key for Project"+find.toString());
		Key key = service.findStateKey(find.getId());
		return new ResponseEntity<Key>(key, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findStateKey2", method = RequestMethod.POST)
	public ResponseEntity<Key>  fndStateKey(@RequestBody ProjectKey skey) {
		logger.info("Find Key for Project"+skey.toString());
		Key key = service.findStateKey(skey);
		return new ResponseEntity<Key>(key, HttpStatus.CREATED);
	}
	
	private List<Field> createFields(int count){
		Long fieldId = 100000L;
		List<Field> fields = new ArrayList<Field>();
		for(int index=0; index < count; index++){
			Field field = new Field();
			field.setId(fieldId);
			field.setName("Field Name");
			fields.add(field);
			fieldId++;
		}
		return fields;
	}
}
