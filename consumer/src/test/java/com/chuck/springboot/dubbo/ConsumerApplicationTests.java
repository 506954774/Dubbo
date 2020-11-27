package com.chuck.springboot.dubbo;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class ConsumerApplicationTests implements Watcher{

	private static CountDownLatch countDownLatch=new CountDownLatch(1);
	private static ZooKeeper zooKeeper=null;
	private static Stat stat=new Stat();



	public static void main(String[] args) {

		String path="/user";

		try {
			ZkClient zkClient = new ZkClient("39.107.40.76:2181",3000);

			 zkClient.setZkSerializer(new ZkSerializer() {
				 @Override
				 public byte[] serialize(Object o) throws ZkMarshallingError {
					 return o.toString().getBytes(StandardCharsets.UTF_8);
				 }

				 @Override
				 public Object deserialize(byte[] bytes) throws ZkMarshallingError {
					 return new String(bytes, StandardCharsets.UTF_8);
				 }
			 });

			IZkDataListener listener=new IZkDataListener() {
				@Override
				public void handleDataChange(String s, Object o) throws Exception {
					System.out.println(String.format("zkClient节点数据改变回调:节点名称:%s,值:%s ", s,o));

				}

				@Override
				public void handleDataDeleted(String s) throws Exception {
					System.out.println(String.format("zkClient节点数据被删除的回调:节点名称:%s ", s ));
				}
			};
			zkClient.subscribeDataChanges(path, listener);




			System.out.println(zkClient.readData(path).toString());

			/*while (true){
				zkClient.writeData(path,"模拟write新的数据:"+new Random().nextInt(100));
				Thread.sleep(5000);
			}*/


			Thread.sleep(Integer.MAX_VALUE);//加上这个,避免main方法执行完毕.否则将释放全部对象,将看不到后续直接操作
			// linux服务器上zookepper后这边的日志

		} catch ( Exception e) {
			System.out.println(e.getStackTrace());

		}
	}


	public static void main1(String[] args) {

		String path="/user";

		try {
			 zooKeeper=new ZooKeeper("39.107.40.76:2181",5000,new ConsumerApplicationTests());
			 countDownLatch.await();
			 System.out.println(new String(zooKeeper.getData(path, true, stat)));

			 Thread.sleep(Integer.MAX_VALUE);


		} catch ( Exception e) {
			System.out.println(e.getStackTrace());

		}
	}


	@Override
	public void process(WatchedEvent watchedEvent) {
           if(Event.KeeperState.SyncConnected==watchedEvent.getState()){
			   if(Event.EventType.None==watchedEvent.getType()&&null!=watchedEvent.getPath()){
				   countDownLatch.countDown();
			   }
			   else if(Event.EventType.NodeDataChanged==watchedEvent.getType()){

				   System.out.println("Event.EventType.NodeDataChanged");


				   try {
					   System.out.println(new String(zooKeeper.getData(watchedEvent.getPath(), true, stat)));
				   } catch ( Exception e) {

				   }
			   }

		   }
	}
}
