package com.pearadmin.modules.mq.service.impl;

import com.netty.mq.util.PropUtil;
import com.netty.mq.util.ReturnT;
import com.pearadmin.common.tools.jsoup.JsoupUtil;
import com.pearadmin.modules.mq.domain.MqCommonRegistry;
import com.pearadmin.modules.mq.domain.MqCommonRegistryData;
import com.pearadmin.modules.mq.domain.MqCommonRegistryMessage;
import com.pearadmin.modules.mq.mapper.MqCommonRegistryDataMapper;
import com.pearadmin.modules.mq.mapper.MqCommonRegistryMapper;
import com.pearadmin.modules.mq.mapper.MqCommonRegistryMessageMapper;
import com.pearadmin.modules.mq.service.MqCommonRegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * registry-admin-impl
 *
 * @author 祝杰汉
 * @date 2024-01-18
 */
@SuppressWarnings("unchecked")
@Service
public class MqCommonRegistryServiceImpl implements MqCommonRegistryService, InitializingBean, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(MqCommonRegistryServiceImpl.class);


    @Resource
    private MqCommonRegistryMapper CommonRegistryDao;
    @Resource
    private MqCommonRegistryDataMapper mqCommonRegistryDataDao;
    @Resource
    private MqCommonRegistryMessageMapper mqCommonRegistryMessageDao;

    @Value("${netty.mq.registry.data.filepath}")
    private String registryDataFilePath;
    @Value("${netty.mq.registry.beattime}")
    private int registryBeatTime;
    @Value("${netty.mq.registry.accessToken}")
    private String accessToken;

    @Override
    public ReturnT<String> registry(String accessToken, List<MqCommonRegistryData> xxlCommonRegistryDataList) {
        // valid
        if (this.accessToken!=null && this.accessToken.trim().length()>0 && !this.accessToken.equals(accessToken)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "AccessToken Invalid");
        }
        if (xxlCommonRegistryDataList==null || xxlCommonRegistryDataList.size()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Invalid.");
        }
        for (MqCommonRegistryData registryData: xxlCommonRegistryDataList) {
            if (registryData.getKey()==null || registryData.getKey().trim().length()==0 || registryData.getKey().trim().length()>255) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Key Invalid[0~255]");
            }
            if (registryData.getValue()==null || registryData.getValue().trim().length()==0 || registryData.getValue().trim().length()>255) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Value Invalid[0~255]");
            }
        }

        // add queue
        registryQueue.addAll(xxlCommonRegistryDataList);

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<String> remove(String accessToken, List<MqCommonRegistryData> xxlCommonRegistryDataList) {
        // valid
        if (this.accessToken!=null && this.accessToken.trim().length()>0 && !this.accessToken.equals(accessToken)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "AccessToken Invalid");
        }
        if (xxlCommonRegistryDataList==null || xxlCommonRegistryDataList.size()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Invalid.");
        }
        for (MqCommonRegistryData registryData: xxlCommonRegistryDataList) {
            if (registryData.getKey()==null || registryData.getKey().trim().length()==0 || registryData.getKey().trim().length()>255) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Key Invalid[0~255]");
            }
            if (registryData.getValue()==null || registryData.getValue().trim().length()==0 || registryData.getValue().trim().length()>255) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "RegistryData Value Invalid[0~255]");
            }
        }

        // add queue
        removeQueue.addAll(xxlCommonRegistryDataList);

        return ReturnT.SUCCESS;
    }

    @Override
    public ReturnT<Map<String, List<String>>> discovery(String accessToken, List<String> keys) {
        // valid
        if (this.accessToken!=null && this.accessToken.trim().length()>0 && !this.accessToken.equals(accessToken)) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "AccessToken Invalid");
        }
        if (keys==null || keys.size()==0) {
            return new ReturnT<>(ReturnT.FAIL_CODE, "keys Invalid.");
        }
        for (String key: keys) {
            if (key==null || key.trim().length()==0 || key.trim().length()>255) {
                return new ReturnT<>(ReturnT.FAIL_CODE, "Key Invalid[0~255]");
            }
        }

        Map<String, List<String>> result = new HashMap<>();
        for (String key: keys) {
            MqCommonRegistryData xxlCommonRegistryData = new MqCommonRegistryData();
            xxlCommonRegistryData.setKey(key);

            List<String> dataList = new ArrayList<>();
            MqCommonRegistry fileMqCommonRegistry = getFileRegistryData(xxlCommonRegistryData);
            if (fileMqCommonRegistry !=null) {
                dataList = fileMqCommonRegistry.getDataList();
            }

            result.put(key, dataList);
        }

        return new ReturnT<>(result);
    }

    @Override
    public DeferredResult<ReturnT<String>> monitor(String accessToken, List<String> keys) {
        // init
        DeferredResult deferredResult = new DeferredResult(registryBeatTime * 3 * 1000L, new ReturnT<>(ReturnT.SUCCESS_CODE, "Monitor timeout."));

        // valid
        if (this.accessToken!=null && this.accessToken.trim().length()>0 && !this.accessToken.equals(accessToken)) {
            deferredResult.setResult(new ReturnT<>(ReturnT.FAIL_CODE, "AccessToken Invalid"));
            return deferredResult;
        }
        if (keys==null || keys.size()==0) {
            deferredResult.setResult(new ReturnT<>(ReturnT.FAIL_CODE, "keys Invalid."));
            return deferredResult;
        }
        for (String key: keys) {
            if (key==null || key.trim().length()==0 || key.trim().length()>255) {
                deferredResult.setResult(new ReturnT<>(ReturnT.FAIL_CODE, "Key Invalid[0~255]"));
                return deferredResult;
            }
        }

        // monitor by client
        for (String key: keys) {
            String fileName = parseRegistryDataFileName(key);

            List<DeferredResult> deferredResultList = registryDeferredResultMap.get(fileName);
            if (deferredResultList == null) {
                deferredResultList = new ArrayList<>();
                registryDeferredResultMap.put(fileName, deferredResultList);
            }

            deferredResultList.add(deferredResult);
        }

        return deferredResult;
    }

    /**
     * update Registry And Message
     */
    private void checkRegistryDataAndSendMessage(MqCommonRegistryData xxlCommonRegistryData){
        // data json
        List<MqCommonRegistryData> xxlCommonRegistryDataList = mqCommonRegistryDataDao.findData(xxlCommonRegistryData.getKey());
        List<String> valueList = new ArrayList<>();
        if (xxlCommonRegistryDataList!=null && xxlCommonRegistryDataList.size()>0) {
            for (MqCommonRegistryData dataItem: xxlCommonRegistryDataList) {
                valueList.add(dataItem.getValue());
            }
        }
        String dataJson = JsoupUtil.writeValueAsString(valueList);

        // update registry and message
        MqCommonRegistry xxlCommonRegistry = CommonRegistryDao.load(xxlCommonRegistryData.getKey());
        boolean needMessage = false;
        if (xxlCommonRegistry == null) {
            xxlCommonRegistry = new MqCommonRegistry();
            xxlCommonRegistry.setKey(xxlCommonRegistryData.getKey());
            xxlCommonRegistry.setData(dataJson);
            CommonRegistryDao.add(xxlCommonRegistry);
            needMessage = true;
        } else {
            if (!xxlCommonRegistry.getData().equals(dataJson)) {
                xxlCommonRegistry.setData(dataJson);
                CommonRegistryDao.update(xxlCommonRegistry);
                needMessage = true;
            }
        }

        if (needMessage) {
            // sendRegistryDataUpdateMessage (registry update)
            sendRegistryDataUpdateMessage(xxlCommonRegistry);
        }

    }

    /**
     * send RegistryData Update Message
     */
    private void sendRegistryDataUpdateMessage(MqCommonRegistry xxlRpcRegistry){
        String registryUpdateJson = JsoupUtil.writeValueAsString(xxlRpcRegistry);

        MqCommonRegistryMessage registryMessage = new MqCommonRegistryMessage();
        registryMessage.setData(registryUpdateJson);
        mqCommonRegistryMessageDao.add(registryMessage);
    }

    // ------------------------ broadcase + file data ------------------------

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private volatile boolean executorStoped = false;
    private volatile List<Integer> readedMessageIds = Collections.synchronizedList(new ArrayList<Integer>());

    private volatile LinkedBlockingQueue<MqCommonRegistryData> registryQueue = new LinkedBlockingQueue<MqCommonRegistryData>();
    private volatile LinkedBlockingQueue<MqCommonRegistryData> removeQueue = new LinkedBlockingQueue<MqCommonRegistryData>();
    private Map<String, List<DeferredResult>> registryDeferredResultMap = new ConcurrentHashMap<>();

    public static MqCommonRegistryData staticRegistryData;

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * registry registry data         (client-num/10 s)
         */
        executorService.execute(() -> {
            while (!executorStoped) {
                try {
                    MqCommonRegistryData xxlCommonRegistryData = registryQueue.take();
                    if (xxlCommonRegistryData !=null) {

                        // refresh or add
                        int ret = mqCommonRegistryDataDao.refresh(xxlCommonRegistryData);
                        if (ret == 0) {
                            mqCommonRegistryDataDao.add(xxlCommonRegistryData);
                        }

                        // valid file status
                        MqCommonRegistry fileMqCommonRegistry = getFileRegistryData(xxlCommonRegistryData);
                        if (fileMqCommonRegistry !=null && fileMqCommonRegistry.getDataList().contains(xxlCommonRegistryData.getValue())) {
                            continue;     // "Repeated limited."
                        }

                        // checkRegistryDataAndSendMessage
                        checkRegistryDataAndSendMessage(xxlCommonRegistryData);
                    }
                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });

        /**
         * remove registry data         (client-num/start-interval s)
         */
        executorService.execute(() -> {
            while (!executorStoped) {
                try {
                    MqCommonRegistryData xxlCommonRegistryData = removeQueue.take();
                    if (xxlCommonRegistryData != null) {

                        // delete
                        mqCommonRegistryDataDao.deleteDataValue(xxlCommonRegistryData.getKey(), xxlCommonRegistryData.getValue());

                        // valid file status
                        MqCommonRegistry fileMqCommonRegistry = getFileRegistryData(xxlCommonRegistryData);
                        if (fileMqCommonRegistry !=null && !fileMqCommonRegistry.getDataList().contains(xxlCommonRegistryData.getValue())) {
                            continue;   // "Repeated limited."
                        }

                        // checkRegistryDataAndSendMessage
                        checkRegistryDataAndSendMessage(xxlCommonRegistryData);
                    }
                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });

        /**
         * broadcase new one registry-data-file     (1/1s)
         *
         * clean old message   (1/10s)
         */
        executorService.execute(() -> {
            while (!executorStoped) {
                try {
                    // new message, filter readed
                    List<MqCommonRegistryMessage> messageList = mqCommonRegistryMessageDao.findMessage(readedMessageIds);
                    if (messageList!=null && messageList.size()>0) {
                        for (MqCommonRegistryMessage message: messageList) {
                            readedMessageIds.add(message.getId());

                            // from registry、add、update、deelete，ne need sync from db, only write
                            MqCommonRegistry xxlCommonRegistry = JsoupUtil.readValue(message.getData(), MqCommonRegistry.class);

                            // default, sync from db （aready sync before message, only write）

                            // sync file
                            setFileRegistryData(xxlCommonRegistry);
                        }
                    }

                    // clean old message;
                    if (System.currentTimeMillis() % registryBeatTime ==0) {
                        mqCommonRegistryMessageDao.cleanMessage(10);
                        readedMessageIds.clear();
                    }
                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });

        /**
         *  clean old registry-data     (1/10s)
         *
         *  sync total registry-data db + file      (1+N/10s)
         *
         *  clean old registry-data file
         */
        executorService.execute(() -> {
            while (!executorStoped) {
                try {

                    // + static registry
                    if (staticRegistryData != null) {
                        registryQueue.add(staticRegistryData);
                    }

                    // clean old registry-data in db
                    mqCommonRegistryDataDao.cleanData(registryBeatTime * 3);

                    // + clean old registry in db
                    CommonRegistryDao.cleanDead();

                    // sync registry-data, db + file
                    int offset = 0;
                    int pagesize = 1000;
                    List<String> registryDataFileList = new ArrayList<>();

                    List<MqCommonRegistry> registryList = CommonRegistryDao.pageList(offset, pagesize);
                    while (registryList!=null && registryList.size()>0) {

                        for (MqCommonRegistry registryItem: registryList) {

                            // default, sync from db
                            List<MqCommonRegistryData> xxlCommonRegistryDataList = mqCommonRegistryDataDao.findData(registryItem.getKey());
                            List<String> valueList = new ArrayList<String>();
                            if (xxlCommonRegistryDataList!=null && xxlCommonRegistryDataList.size()>0) {
                                for (MqCommonRegistryData dataItem: xxlCommonRegistryDataList) {
                                    valueList.add(dataItem.getValue());
                                }
                            }
                            String dataJson = JsoupUtil.writeValueAsString(valueList);

                            // check update, sync db
                            if (!registryItem.getData().equals(dataJson)) {
                                registryItem.setData(dataJson);
                                CommonRegistryDao.update(registryItem);
                            }

                            // sync file
                            String registryDataFile = setFileRegistryData(registryItem);

                            // collect registryDataFile
                            registryDataFileList.add(registryDataFile);
                        }


                        offset += 1000;
                        registryList = CommonRegistryDao.pageList(offset, pagesize);
                    }

                    // clean old registry-data file
                    cleanFileRegistryData(registryDataFileList);

                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(registryBeatTime);
                } catch (Exception e) {
                    if (!executorStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        });


    }

    @Override
    public void destroy() throws Exception {
        executorStoped = true;
        executorService.shutdownNow();
    }


    // ------------------------ file opt ------------------------

    // get
    public MqCommonRegistry getFileRegistryData(MqCommonRegistryData xxlCommonRegistryData){

        // fileName
        String fileName = parseRegistryDataFileName(xxlCommonRegistryData.getKey());

        // read
        Properties prop = PropUtil.loadProp(fileName);
        if (prop!=null) {
            MqCommonRegistry fileMqCommonRegistry = new MqCommonRegistry();
            fileMqCommonRegistry.setData(prop.getProperty("data"));
            fileMqCommonRegistry.setDataList(JsoupUtil.readValue(fileMqCommonRegistry.getData(), List.class));
            return fileMqCommonRegistry;
        }
        return null;
    }
    private String parseRegistryDataFileName(String key){
        // fileName
        String fileName = registryDataFilePath
                .concat(File.separator).concat(key)
                .concat(".properties");
        return fileName;
    }

    // set
    public String setFileRegistryData(MqCommonRegistry xxlCommonRegistry){

        // fileName
        String fileName = parseRegistryDataFileName(xxlCommonRegistry.getKey());

        // valid repeat update
        Properties existProp = PropUtil.loadProp(fileName);
        if (existProp != null && existProp.getProperty("data").equals(xxlCommonRegistry.getData())
                ) {
            return new File(fileName).getPath();
        }

        // write
        Properties prop = new Properties();
        prop.setProperty("data", xxlCommonRegistry.getData());

        PropUtil.writeProp(prop, fileName);

        logger.info(">>>>>>>>>>> xxl-mq, setFileRegistryData: key={}, data={}", xxlCommonRegistry.getKey(), xxlCommonRegistry.getData());


        // brocast monitor client
        List<DeferredResult> deferredResultList = registryDeferredResultMap.get(fileName);
        if (deferredResultList != null) {
            registryDeferredResultMap.remove(fileName);
            for (DeferredResult deferredResult: deferredResultList) {
                deferredResult.setResult(new ReturnT<>(ReturnT.SUCCESS_CODE, "Monitor key update."));
            }
        }

        return new File(fileName).getPath();
    }
    // clean
    public void cleanFileRegistryData(List<String> registryDataFileList){
        filterChildPath(new File(registryDataFilePath), registryDataFileList);
    }

    public void filterChildPath(File parentPath, final List<String> registryDataFileList){
        if (!parentPath.exists() || parentPath.list()==null || parentPath.list().length==0) {
            return;
        }
        File[] childFileList = parentPath.listFiles();
        for (File childFile: childFileList) {
            if (childFile.isFile() && !registryDataFileList.contains(childFile.getPath())) {
                childFile.delete();

                logger.info(">>>>>>>>>>> xxl-mq, cleanFileRegistryData, RegistryData Path={}", childFile.getPath());
            }
            if (childFile.isDirectory()) {
                if (parentPath.listFiles()!=null && parentPath.listFiles().length>0) {
                    filterChildPath(childFile, registryDataFileList);
                } else {
                    childFile.delete();
                }

            }
        }

    }


}
