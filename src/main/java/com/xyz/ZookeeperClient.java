package com.xyz;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 类: ZookeeperClient <br>
 * 描述: zookeeper客户端<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年04月28日 17:57
 */
public class ZookeeperClient {

//    private static final String HOST_PORT = "192.168.202.128:2181";  //公司的zookeeper地址
    private static final String HOST_PORT = "192.168.1.120:2181";      //家里的zookeeper地址
    private static final int TIME_OUT = 5000;
    private static String CHAR_SET = "UTF-8";
    private ZooKeeper zooKeeper;
    private ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher();

    /**
     * 构造方法私有化
     */
    private ZookeeperClient(){
        zookeeperInit();
    }

    private void zookeeperInit(){
        try {
            zooKeeper = new ZooKeeper(HOST_PORT,TIME_OUT,zookeeperWatcher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ZookeeperClientHolder{
        private static ZookeeperClient zookeeperClient = new ZookeeperClient();
    }

    public static ZookeeperClient getZkInstance(){
        return ZookeeperClientHolder.zookeeperClient;
    }

    /**
     * 对节点添加监听
     * @param path
     */
    public void watchChanged(String path){
        try {
            zooKeeper.getChildren(path,zookeeperWatcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建持久的节点
     */
    public String createPersistentNode(String path,String context) throws Exception{
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 创建顺序化的持久节点
     * @param path
     * @param context
     */
    public String createPersistentSequentialNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
    }

    /**
     * 创建临时节点
     * @param path
     * @param context
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    /**
     * 创建临时顺序节点
     * @param path
     * @param context
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String createEphemeralSequentialNode(String path,String context) throws Exception {
        return zooKeeper.create(path, context.getBytes(CHAR_SET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    /**
     * 删除节点
     * @param path
     */
    public void deleteNode(String path,int... version) throws Exception {
        if (version.length==0){
            zooKeeper.delete(path,-1);
        }else{
            for (int i =0;i<version.length;i++){
                zooKeeper.delete(path, version[i]);
            }
        }
    }

    /**
     * 更新节点数据
     * @param path
     * @param context
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void updateNodeData(String path,String context) throws Exception {
        zooKeeper.setData(path,context.getBytes(CHAR_SET),-1);
    }

    public String getNodeData(String path) throws Exception{
       return new String(zooKeeper.getData(path,false,null));
    }

}
