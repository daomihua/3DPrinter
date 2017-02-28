package com.printer.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.printer.dao.ModelDao;
import com.printer.entities.Model;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ModelTest {
	
	@Autowired
	ModelDao modeldao;
	
	//��ѯ ģ��
//	@Test
//	public void queryModel(){
//		Model model = new Model();
//		model=modeldao.queryModel(1);
//		System.out.println(model.getModelName());
////		string data = DateTime.Now.ToString("yyyyMMdd") + BitConverter.ToInt32(Guid.NewGuid().ToByteArray(), 0).ToString();
//        
//	}
	
	//��ѯģ���б�
//	@Test
//	public void queryModelList(){
//		List<Model> list = new ArrayList<Model>();
//		list = modeldao.queryModelList();
//		System.out.println(list.size()+"   "+list.get(1).getClassName());
//	}
	
	//���ģ��
//	@Test
//	public void addModel(){
//		Model m = new Model();
//		m.setModelName("Բ���׹");
//		m.setClassID(1);
//		m.setModelFile("xxxx");
//		m.setModelPrice(20.0);
//		m.setModelSPic("xxxxx");
//		m.setModelBPic1("xxxxx");
//		int r=modeldao.addModel(m);
//		System.out.println(r);
//	}
	
	//������ѯģ��
	@Test
	public void queryBy(){
		List<Model> list = new ArrayList<Model>();
		list = modeldao.queryModelBy("ClassID", "1", 5);

	}

}
