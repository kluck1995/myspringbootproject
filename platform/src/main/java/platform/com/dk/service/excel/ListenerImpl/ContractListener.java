package platform.com.dk.service.excel.ListenerImpl;

import com.dk.common.entity.FinancingAssetContract;
import com.dk.common.components.easyexcel.listenner.EasyExcelListenner;
import com.dk.common.mapper.FinancingAssetContractMapper;
import org.springframework.stereotype.Component;
import platform.com.dk.service.excel.utils.SpringBeanUtil;
import java.util.List;

/**
 * 指定操作实体类型的监听器
 * 继承基础监听器
 */
@Component
public class ContractListener extends EasyExcelListenner {
/*
    @Autowired
    private FinancingAssetContractMapper financingAssetContractMapper;
*/

    @Override
    public void doService(List list) {
        // Filter和Listener加载顺序优先于spring容器初始化实例，所以使用@Autowired肯定为null了
        // 通过ApplicationContext上下文对象实例获取bean
        FinancingAssetContractMapper financingAssetContractMapper = SpringBeanUtil.getBean(FinancingAssetContractMapper.class);

        // 尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
        for (Object o : list) {
            FinancingAssetContract contract = (FinancingAssetContract) o;
            // 入库
            financingAssetContractMapper.insert(contract);
        }
    }
}
