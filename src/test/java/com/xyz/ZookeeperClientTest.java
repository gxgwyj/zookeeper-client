package com.xyz;

import org.junit.Test;

/**
 * Created by Lenovo on 2018/5/5.
 */
public class ZookeeperClientTest {
    private ZookeeperClient zkCient = ZookeeperClient.getZkInstance();


    @Test
    public void createPersistentNodeTest() throws Exception {
        String path = "/gaoxugang";
        String context = "高旭刚";
        String result = zkCient.createPersistentNode(path,context);
        System.out.println(result);
    }

    @Test
    public void getNodeDataTest() throws Exception{
        String path = "/gaoxugang";
        String result = zkCient.getNodeData(path);
        System.out.println(result);
    }

    @Test
    public void deleteNodeTest() throws Exception {
        String path = "/gaoxugang";
        zkCient.deleteNode(path);
    }

    @Test
    public void updateNodeDataTest() throws Exception {
        String path = "/gaoxugang";
        zkCient.updateNodeData(path,"张三");
    }




}
