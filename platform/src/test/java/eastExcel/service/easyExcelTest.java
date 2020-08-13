package eastExcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dk.common.mapper.FinancingAssetContractMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import platform.PlatformApplication;
import com.dk.common.entity.User;
import com.dk.common.entity.FinancingAssetContract;
import platform.com.dk.service.excel.ListenerImpl.ContractListener;
import java.util.Arrays;
import java.util.List;

/**
 * easyExcel 使用案例演示
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlatformApplication.class)
public class easyExcelTest {
    @Autowired
    private FinancingAssetContractMapper financingAssetContractMapper;

    /**
     * 导出到excel
     */
    @Test
    public void writeExcelTest1() {
        // String s = new DateTime().toString("yyyy-MM-dd hh:mm:ss");
        QueryWrapper<FinancingAssetContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FinancingAssetContract::getDeleted, 0);
        List<FinancingAssetContract> financingAssetContracts = financingAssetContractMapper.selectList(queryWrapper);
        // 导出
        String path = "/Users/dongkai/Desktop/合同列表模板1111.xlsx";
        // 创建esayExcel的写出类构造器
        // 指定excel将要写出到什么位置，以及excel中的数据是基于哪个java对象创建的
        ExcelWriter excelWriter = EasyExcel.write(path, FinancingAssetContract.class).build();
        // 创建sheet构造器
        WriteSheet sheet = EasyExcel.writerSheet("test").build();
        // 将数据写入到sheet中
        excelWriter.write(financingAssetContracts, sheet);
        // 关闭流
        excelWriter.finish();
        System.out.println("执行成功");
    }

    /**
     * 导出到excel
     * 最简写法  链式表达
     */
    @Test
    public void writeExcelTest2() {
        // String s = new DateTime().toString("yyyy-MM-dd hh:mm:ss");
        QueryWrapper<FinancingAssetContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FinancingAssetContract::getDeleted, 0);
        List<FinancingAssetContract> financingAssetContracts = financingAssetContractMapper.selectList(queryWrapper);
        // 导出
        String path = "/Users/dongkai/Desktop/合同列表模板1111.xlsx";
        EasyExcel.write(path, FinancingAssetContract.class).sheet("sheet名称").doWrite(financingAssetContracts);
        System.out.println("执行成功");
    }

    /**
     * 从excel导入  读取
     */
    @Test
    public void readExcelSchemeTwoTest1() {
        // 读取
        String path = "/Users/dongkai/Desktop/合同列表模板.xlsx";
        // 创建读入excel的构造器
        // 指定读取excel的位置，以及要读取成哪个java对象
        // 需要配置读取的监听器，如果不配置无法读取
        ExcelReader excelReader = EasyExcel.read(path, FinancingAssetContract.class, new ContractListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
    }

    /**
     * 从excel导入  读取
     * 最简写法
     */
    @Test
    public void readExcelSchemeOneTest2() {
        // 读取
        String path = "/Users/dongkai/Desktop/合同列表模板.xlsx";
        EasyExcel.read(path, FinancingAssetContract.class, new ContractListener()).sheet().doRead();
    }

    /**
     * 导出到excel
     * 最简写法  链式表达
     */
    @Test
    public void writeExcelUser() {
        User user1 = User.builder().name("laowang11").phone("13200001111").email("1221321312@qq.com").address("地址啊啊大大大").build();
        User user2 = User.builder().name("laowang22").phone("13200002222").email("1221321312@qq.com").address("地址啊啊大大大").build();
        User user3 = User.builder().name("laowang33").phone("13200003333").email("1221321312@qq.com").address("地址啊啊大大大").build();
        User user4 = User.builder().name("laowang44").phone("13200004444").email("1221321312@qq.com").address("地址啊啊大大大").build();
        List<User> users = Arrays.asList(user1, user2, user3, user4);

        // 导出
        String path = "/Users/dongkai/Desktop/用户.xlsx";
        EasyExcel.write(path, User.class).sheet("用户表单111").doWrite(users);
        System.out.println("执行成功");
    }


}
