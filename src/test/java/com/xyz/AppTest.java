package com.xyz;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) throws Exception {
        ZookeeperClient zkCient = ZookeeperClient.getZkInstance();
        String nodeData = zkCient.getNodeData("/gaoxugang");
        System.out.println(nodeData);
    }
}
