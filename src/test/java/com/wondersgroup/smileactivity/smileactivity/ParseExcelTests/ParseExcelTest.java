package com.wondersgroup.smileactivity.smileactivity.ParseExcelTests;

import com.wondersgroup.smileactivity.smileactivity.entity.TbVideo;
import com.wondersgroup.smileactivity.smileactivity.repository.TbVideoRepository;
import com.wondersgroup.smileactivity.smileactivity.utils.ExcelUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class ParseExcelTest {

    @Autowired
    private TbVideoRepository tbVideoRepository;

    /**
     * excle表格批量导入视频
     *
     * @throws Exception
     */
    @Test
//    @Transactional(rollbackFor = Exception.class)
    public void addStaffs() throws Exception {
        int i = 0;
        String filePath = "C:/Users/ymx/Desktop/ceshi1.xlsx";  //excel文件路径
        File excelFile = new File(filePath);
        List<TbVideo> videos = convertToVo(excelFile);
        for(TbVideo v :videos){
            tbVideoRepository.save(v);
            i++;
        }
        System.out.println("添加成功"+i+"条数据");
    }

    /**
     * 把Excel表格转换为list
     *
     * @param file 文件
     * @return 返回封装后视频信息
     * @throws Exception 抛出文件解析异常
     */
    private List<TbVideo> convertToVo(File file) throws Exception {
        List<List<String>> lists = ExcelUtil.convertExcel(file);
        // 移除第一行
        lists.remove(0);
        // 统计行数
        int rowNum = 0;
        List<TbVideo> videos = new ArrayList<>();
        for (List<String> list : lists) {
            rowNum++;
            int lowNum = 0;
            if (StringUtils.isEmpty(list.get(lowNum))) {
                break;
            }
            // 赛到staff中
            TbVideo video = new TbVideo();
            video.setVideoId(list.get(0).trim().split("\\.")[0]);
            video.setVideoType(list.get(1).trim());
            video.setVideoSourceIntroduce(list.get(2).trim());
            video.setVideoName(list.get(3).trim());
            video.setVideoDescribe(list.get(5).trim());
            video.setVideoImage(list.get(6).trim());
            video.setVideoPlayLink(list.get(7).trim());
            videos.add(video);
        }
        return videos;
    }
}
