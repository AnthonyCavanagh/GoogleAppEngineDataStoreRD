package com.cav.googlecloud.service.datastore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cav.googlecloud.model.data.AddFields;
import com.cav.googlecloud.model.data.AddState;
import com.cav.googlecloud.model.data.Field;
import com.cav.googlecloud.model.data.Project;
import com.cav.googlecloud.model.data.State;
import com.cav.googlecloud.model.data.ProjectKey;
import com.cav.googlecloud.model.data.Type;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;




@Service
public class DataStorageServiceImpl implements DataStorageService{

	@Autowired
	private DatastoreService datastoreService;
	
	private static final Logger logger = LoggerFactory.getLogger(DataStorageServiceImpl.class);
	
	@Override
	public Type addType(Type type) {
		Entity entity = new Entity("Type", type.getId());
		
		try {
			entity = datastoreService.get(entity.getKey());
			entity.setProperty("Id", type.getId());
			entity.setProperty("Name", type.getName());
			EmbeddedEntity stateEntity = new EmbeddedEntity();
			State state = type.getState();
			if(state != null){
				stateEntity.setProperty("Id", state.getId());
				stateEntity.setProperty("Name", state.getName());
				entity.setProperty("State", stateEntity);
			}
		} catch (EntityNotFoundException e) {
			entity.setProperty("Id", type.getId());
			entity.setProperty("Name", type.getName());
			EmbeddedEntity stateEntity = new EmbeddedEntity();
			State state = type.getState();
			if(state != null){
				stateEntity.setProperty("Id", state.getId());
				stateEntity.setProperty("Name", state.getName());
				entity.setProperty("State", stateEntity);
			}
		}
		datastoreService.put(entity);
		return type;
	}

	@Override
	public Type findType(Long id){
		Entity entity = new Entity("Type", id);
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			return mapType(props);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Type();
	}
	
	@Override
	public Key findTypeKey(Long id){
		 logger.info("findTypeKey");
		Entity entity = new Entity("Type", id);
		
		try {
			entity = datastoreService.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity.getKey();
	}
	
	@Override
	public Key findTypeSateKey(Long id){
		logger.info("findTypeStateKey");
		Entity entity = new Entity("Type", id);
		
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			EmbeddedEntity stateEntity = (EmbeddedEntity) props.get("State");
			logger.info("Return Type State Key "+stateEntity.getKey());
			return stateEntity.getKey();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity.getKey();
	}

	@Override
	public State addState(State state) {
		Entity entity = new Entity("State", state.getId());
		try {
			entity = datastoreService.get(entity.getKey());
			entity.setProperty("Id", state.getId());
			entity.setProperty("Name", state.getName());
		} catch (EntityNotFoundException e) {
			entity.setProperty("Id", state.getId());
			entity.setProperty("Name", state.getName());
		}
		datastoreService.put(entity);
		return state;
	}
	
	@Override
	public Project addProject(Project project) {
		Entity entity = new Entity("Project", project.getId());
		try {
			entity = datastoreService.get(entity.getKey());
			entity.setProperty("Id", project.getId());
			entity.setProperty("Name", project.getName());
		} catch (EntityNotFoundException e) {
			entity.setProperty("Id", project.getId());
			entity.setProperty("Name", project.getName());
		}
		datastoreService.put(entity);
		return project;
	}
	
	@Override
	public AddState addState(AddState cs) {
		logger.info("Add state "+cs.toString());
		Entity entity = new Entity("Project", cs.getProjectId());
		try {
			entity = datastoreService.get(entity.getKey());
			State state = cs.getState();
			Entity stateEntity = new Entity("State", state.getId(), entity.getKey());
			stateEntity.setProperty("Id", state.getId());
			stateEntity.setProperty("Name", state.getName());
			datastoreService.put(stateEntity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return cs;
	}
	
	@Override
	public Key findProjectKey(Long id){
		logger.info("find Project Key");
		Entity entity = new Entity("Project", id);		
		try {
			entity = datastoreService.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity.getKey();
	}
	
	@Override
	public Key findStateKey(Long id){
		logger.info("find State Key");
		Entity entity = new Entity("State", id);		
		try {
			entity = datastoreService.get(entity.getKey());
			return entity.getKey();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Key findStateKey(ProjectKey key){
		logger.info("find Project StateKey");
		Entity entity = new Entity("Project", key.getProjectId());		
		try {
			Entity stateEntity = new Entity("State", key.getStateId(), entity.getKey());
			logger.info("State Entity Key "+stateEntity.getKey());
			entity = datastoreService.get(stateEntity.getKey());
			return stateEntity.getKey();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Project findProject(ProjectKey key){
		logger.info("find Project project");
		Entity entity = new Entity("Project", key.getProjectId());
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			Project project = mapProject(props);
			Entity stateEntity = new Entity("State", key.getStateId(), entity.getKey());
			stateEntity = datastoreService.get(stateEntity.getKey());
			if(stateEntity != null){
				project.setState(mapState(stateEntity.getProperties()));
			} else {
				logger.info("stateEntity is null");
			}
			return project;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Project();
	}
	
    private Type mapType(Map<String, Object> props){
    	Type type = new Type();
    	type.setId((Long)props.get("Id"));
    	type.setName((String)props.get("Name"));
    	EmbeddedEntity stateEntity = (EmbeddedEntity) props.get("State");
    	if(stateEntity != null){
    		type.setState(mapState(stateEntity.getProperties()));
    	}
		return type;
    }
    
    private Project mapProject(Map<String, Object> props){
    	Project project = new Project();
    	project.setId((Long)props.get("Id"));
    	project.setName((String)props.get("Name"));
		return project;
    }
    
    private State mapState(Map<String, Object> props){
    	State state = new State();
    	state.setId((Long)props.get("Id"));
    	state.setName((String)props.get("Name"));
		return state;
    }

	@Override
	public String createFieldsProject(AddFields fields) {
		long startTime = System.nanoTime();
		Entity entity = new Entity("Project", fields.getId());		
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			props.get("FieldIds");
			List <Long> fieldIds = (List<Long>) props.get("FieldIds");
			if(fieldIds == null){
				fieldIds = new ArrayList<Long>();
			}
			for(Field field : fields.getFields()){
				if(mapFieldProject(field)){
					//Dont want duplicate Ids
					fieldIds.add(field.getId());
				}
			}
			entity.setProperty("FieldIds", fieldIds);
			datastoreService.put(entity);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			StringBuilder sb = new StringBuilder();
			sb.append("Start Time : ")
			.append(startTime)
			.append(" End Time : ")
			.append(endTime)
			.append(" duration : ")
			.append(duration);
			return sb.toString();
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String createFieldsType(AddFields fields) {
		long startTime = System.nanoTime();
		Entity entity = new Entity("Type", fields.getId());		
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			
			Map <Long, Field> fieldMap = new ConcurrentHashMap<Long, Field>();
			Blob blob = (Blob) props.get("Fields");
			if(blob != null){
				byte[] byteOut =  blob.getBytes();
				fieldMap = convertByteToMap(byteOut);
			}
			for(Field field : fields.getFields()){
				if(!fieldMap.containsKey(field.getId())){
					fieldMap.put(field.getId(), field);
				}
			}
			byte[] fieldBytes = convertMapToBytes(fieldMap);
			blob = new Blob(fieldBytes);
			entity.setProperty("Fields", blob);
			datastoreService.put(entity);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			StringBuilder sb = new StringBuilder();
			sb.append("Start Time : ")
			.append(startTime)
			.append(" End Time : ")
			.append(endTime)
			.append(" duration : ")
			.append(duration);
			return sb.toString();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String findFieldsProject(Long id) {
		List <Field> fields = new ArrayList<Field> ();
		long startTime = System.nanoTime();
		Entity entity = new Entity("Project", id);
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			List <Long>fieldIds = (List<Long>) props.get("FieldIds");
			if(fieldIds != null){
				for(Long fId : fieldIds){
					fields.add(mapEntityToField(fId));
				}
			}
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			logger.info("Fields Array "+fields.toString());
			StringBuilder sb = new StringBuilder();
			sb.append("Start Time : ")
			.append(startTime)
			.append(" End Time : ")
			.append(endTime)
			.append(" duration : ")
			.append(duration);
			return sb.toString();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Fail";	
	}

	@Override
	public String findFieldsType(Long id) {
		List <Field> fields = new ArrayList<Field> ();
		long startTime = System.nanoTime();
		Entity entity = new Entity("Type", id);
		try {
			entity = datastoreService.get(entity.getKey());
			Map<String, Object> props = entity.getProperties();
			Blob blob = (Blob) props.get("Fields");
			if(blob != null){
				byte[] byteOut =  blob.getBytes();
				Map<Long, Field> fieldMap = convertByteToMap(byteOut);
				fields = fieldMap.entrySet().stream()
			                .map(x -> x.getValue())
			                .collect(Collectors.toList());

			}
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			logger.info("Fields Array "+fields.toString());
			StringBuilder sb = new StringBuilder();
			sb.append("Start Time : ")
			.append(startTime)
			.append(" End Time : ")
			.append(endTime)
			.append(" duration : ")
			.append(duration);
			return sb.toString();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Fail";		
	}
	
	private boolean mapFieldProject(Field field){
		Entity entity = new Entity("Field", field.getId());
		try {
			entity = datastoreService.get(entity.getKey());
			return false;
		} catch (EntityNotFoundException e) {
			entity.setProperty("Id", field.getId());
			entity.setProperty("Name", field.getName());
			datastoreService.put(entity);
			return true;
		}
	}
	
	
	private byte[] convertMapToBytes(Map<Long, Field>  map) throws IOException{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);
	    out.writeObject(map);
	    return byteOut.toByteArray();
	}
	
	private Map<Long, Field> convertByteToMap(byte[] bytes) throws IOException, ClassNotFoundException{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(bytes.length);
		byteOut.write(bytes);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
	    ObjectInputStream in = new ObjectInputStream(byteIn);
	    Map<Long, Field> map = (Map<Long, Field>) in.readObject();
		return map;
	}
	
	private Field mapEntityToField(Long id){
		Field field = new Field();
		Entity entity = new Entity("Field", id);
		try {
			entity = datastoreService.get(entity.getKey());
			field.setId((Long)entity.getProperty("Id"));
			field.setName((String)entity.getProperty("Name"));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return field;
	}
}
