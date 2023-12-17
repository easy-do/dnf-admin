package plus.easydo.dnf.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.mybatisflex.core.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2022/12/20 16:25
 * @Description 通用excel导入数据监听器
 */
@Slf4j
public class BaseDataListener<T> implements ReadListener<T> {


    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private final IService<T> service;


    /**
     * 构造传入service
     *
     * @param baseService baseService
     * @author laoyu
     * @date 2022/12/20
     */
    public BaseDataListener(IService<T> baseService) {
        this.service = baseService;
    }

    /**
     * 每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        service.saveBatch(cachedDataList);
        log.info("存储数据库成功！");
    }
}
