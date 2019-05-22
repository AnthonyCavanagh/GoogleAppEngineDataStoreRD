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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.cav.googlecloud.model.data.Field;
import com.cav.googlecloud.model.data.State;

@RunWith(SpringRunner.class)
//@Import({DataStorageServiceImpl.class})
public class DataStorageServiceTest {

	//@Autowired
	//DataStorageService service;
	
	@Test
	public void DataStoreTest(){
		State state = new State();
		state.setId(111011L);
		state.setName("State 2");
		//state = service.addState(state);
		System.out.println(state.toString());
		createFields(5);
	}
	
	@Test
	public void testDataStorage() throws IOException, ClassNotFoundException{
		byte[] byteOut = convertMapToBytes(CreateMap());
		Map<Long, Field> map = convertByteToMap(byteOut);
		System.out.println(map.toString());
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
	
	private Map<Long, Field> CreateMap(){
		Map <Long, Field>map = new ConcurrentHashMap<Long, Field>();
		Field field = new Field();
		field.setId(100100L);
		field.setName("Field A");
		map.put(field.getId(), field);
		return map;
	}
	
	private List<Field> createFields(int count){
		Long fieldId = 100000L;
		List<Field> fields = new ArrayList<Field>();
		for(int index=0; index < count; index++){
			Field field = new Field();
			field.setId(fieldId);
			field.setName("Field Name");
			fieldId++;
		}
		return fields;
	}
}
