package com.netty.mq.consumer.registry;

import com.netty.mq.consumer.annotation.MqConsumer;
import com.netty.mq.consumer.thread.ConsumerThread;
import com.xxl.registry.client.model.XxlRegistryDataParamVO;
import com.xxl.rpc.registry.impl.XxlRegistryServiceRegistry;

import java.util.*;

/**
 * client-registry-helper
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
public class ConsumerRegistryHelper {

    private static XxlRegistryServiceRegistry serviceRegistry;
    public ConsumerRegistryHelper(XxlRegistryServiceRegistry serviceRegistry) {
        ConsumerRegistryHelper.serviceRegistry = serviceRegistry;
    }


    // ---------------------- util ----------------------
    private static final String SpaceMark = "_consumer_";

    private static String makeRegistryKey(String topic){
        String registryKey = SpaceMark.concat(topic);                       // _consumer_{topic01}
        return registryKey;
    }
    private static String makeRegistryValPrefix(String group){
        String registryValPrefix = group.concat(SpaceMark);                 // {group01}_consumer_***
        return registryValPrefix;
    }
    private static String makeRegistryVal(String group, String consumerUuid){
        String registryValPrefix = makeRegistryValPrefix(group);
        String registryVal = registryValPrefix.concat(consumerUuid);        // {group01}_consumer_{uuid}
        return registryVal;
    }
    private static String parseGroupFromRegistryVal(String registryVal){
        String[] onlineConsumerItemArr = registryVal.split(SpaceMark);
        if (onlineConsumerItemArr!=null && onlineConsumerItemArr.length>1) {
            String group = onlineConsumerItemArr[0];
            return group;
        }
        return null;
    }

    // ---------------------- api ----------------------

    /**
     * consumer registry
     *
     * @param consumerThreadList
     */
    public static void registerConsumer(List<ConsumerThread> consumerThreadList) {

        List<XxlRegistryDataParamVO> registryParamList = new ArrayList<>();
        Set<String> registryParamKeyList = new HashSet<>();

        for (ConsumerThread consumerThread: consumerThreadList) {
            String registryKey = makeRegistryKey(consumerThread.getMqConsumer().topic());
            String registryVal = makeRegistryVal(consumerThread.getMqConsumer().group(), consumerThread.getUuid());

            registryParamList.add(new XxlRegistryDataParamVO(registryKey, registryVal));
            registryParamKeyList.add(registryKey);
        }

        // registry mult consumer
        serviceRegistry.getXxlRegistryClient().registry(registryParamList);

        // discovery mult consumer
        serviceRegistry.getXxlRegistryClient().discovery(registryParamKeyList);
    }

    /**
     * consumer registry remove
     */
    public void removeConsumer(List<ConsumerThread> consumerThreadList){
        List<XxlRegistryDataParamVO> registryParamList = new ArrayList<>();
        for (ConsumerThread consumerThread: consumerThreadList) {
            String registryKey = makeRegistryKey(consumerThread.getMqConsumer().topic());
            String registryVal = makeRegistryVal(consumerThread.getMqConsumer().group(), consumerThread.getUuid());
            registryParamList.add(new XxlRegistryDataParamVO(registryKey, registryVal));
        }

        serviceRegistry.getXxlRegistryClient().remove(registryParamList);
    }

    /**
     * isActice
     *
     * @param consumerThread
     * @return
     */
    public ActiveInfo isActice(ConsumerThread consumerThread){
        // init data
        String registryKey = makeRegistryKey(consumerThread.getMqConsumer().topic());
        String registryValPrefix = makeRegistryValPrefix(consumerThread.getMqConsumer().group());
        String registryVal = makeRegistryVal(consumerThread.getMqConsumer().group(), consumerThread.getUuid());

        // load all consumer
        TreeSet<String> onlineConsumerSet = serviceRegistry.discovery(registryKey);
        if (onlineConsumerSet==null || onlineConsumerSet.size()==0) {
            return null;
        }

        // filter by group
        TreeSet<String> onlineConsumerSet_group = new TreeSet<String>();
        for (String onlineConsumerItem : onlineConsumerSet) {
            if (onlineConsumerItem.startsWith(registryValPrefix)) {
                onlineConsumerSet_group.add(onlineConsumerItem);
            }
        }
        if (onlineConsumerSet_group==null || onlineConsumerSet_group.size()==0) {
            return null;
        }

        // rank
        int rank = -1;
        for (String onlineConsumerItem : onlineConsumerSet_group) {
            rank++;
            if (onlineConsumerItem.equals(registryVal)) {
                break;
            }
        }
        if (rank == -1) {
            return null;
        }

        return new ActiveInfo(rank, onlineConsumerSet_group.size(), onlineConsumerSet_group.toString());
    }

    /**
     * get total group list
     */
    public Set<String> getTotalGroupList(String topic){
        // init data
        String registryKey = makeRegistryKey(topic);


        // load all consumer, find all groups
        Set<String> groupSet = new HashSet<>();
        TreeSet<String> onlineConsumerRegistryValList = serviceRegistry.discovery(registryKey);

        if (onlineConsumerRegistryValList!=null && onlineConsumerRegistryValList.size()>0) {
            for (String onlineConsumerRegistryValItem : onlineConsumerRegistryValList) {
                String groupItem = parseGroupFromRegistryVal(onlineConsumerRegistryValItem);
                if (groupItem!=null && groupItem.length()>1) {
                    groupSet.add(groupItem);
                }
            }
        }

        if (!groupSet.contains(MqConsumer.DEFAULT_GROUP)) {
            groupSet.add(MqConsumer.DEFAULT_GROUP);
        }
        return groupSet;
    }

    public static class ActiveInfo{
        // consumer rank
        public int rank;
        // alive num
        public int total;
        // registry rank info
        public String registryRankInfo;

        public ActiveInfo(int rank, int total, String registryRankInfo) {
            this.rank = rank;
            this.total = total;
            this.registryRankInfo = registryRankInfo;
        }

        @Override
        public String toString() {
            return "ActiveInfo{" +
                    "rank=" + rank +
                    ", total=" + total +
                    ", registryRankInfo='" + registryRankInfo + '\'' +
                    '}';
        }
    }

}
