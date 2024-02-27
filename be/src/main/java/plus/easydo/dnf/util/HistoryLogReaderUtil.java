package plus.easydo.dnf.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import plus.easydo.dnf.entity.DaGameEvent;
import plus.easydo.dnf.enums.HistoryLogTypeEnum;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-02-02 11:28
 * @Description 服务端历史日志分析工具
 */
public class HistoryLogReaderUtil {

    private static final List<String> GET_TYPE = HistoryLogTypeEnum.allType();

    private HistoryLogReaderUtil() {
    }

    public static List<DaGameEvent> reader(File file){
        List<String> lines = FileUtil.readLines(file, CharsetUtil.defaultCharset());
        List<DaGameEvent> list = new LinkedList<>();
        String fileName = file.getName();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> lineTexts = CharSequenceUtil.split(line, ",");
            if (lineTexts.size() >= 17) {
                if(GET_TYPE.contains(lineTexts.get(13).trim())){
                    DaGameEvent gameEvent = DaGameEvent.builder()
                            .fileIndex(i)
                            .fileName(fileName)
                            .accountId(Long.valueOf(lineTexts.get(1)))
                            .optionTime(textToLocalDateTime(lineTexts.get(3)))
                            .charcaName(CharSequenceUtil.removeAll(lineTexts.get(4),"\""))
                            .charcaNo(Long.valueOf(lineTexts.get(5)))
                            .level(Integer.valueOf(lineTexts.get(6)))
                            .clientIp(lineTexts.get(10))
                            .channel(lineTexts.get(12))
                            .optionType(lineTexts.get(13).trim())
                            .param1(lineTexts.get(14).trim())
                            .param2(lineTexts.get(15).trim())
                            .param3(lineTexts.get(16).trim())
                            .build();
                    list.add(gameEvent);
                }
            }
        }
        return list;
    }

    private static LocalDateTime textToLocalDateTime(String text){
        //text : 161700
        String yearMonthDay = LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd");
        return LocalDateTimeUtil.parse(yearMonthDay+text,"yyyyMMddHHmmss");
    }

    public static List<File> readerAllHistoryLog(String path){
        List<File> allFilelist = FileUtil.loopFiles(path);
        String yearMonthDay = LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<File> historyFileList = new ArrayList<>();
        allFilelist.forEach(file -> {
            String fileName = file.getName();
            if(fileName.contains(yearMonthDay) && fileName.contains(".history")){
                historyFileList.add(file);
            }
        });
        return historyFileList;
    }

    public static List<File> getCurrentHistoryLog(List<File> historyFileList){
        String hour = LocalDateTimeUtil.format(LocalDateTime.now(), "HH");
        List<File> currentTimeFile = new ArrayList<>();
        historyFileList.forEach(historyFile->{
            if(historyFile.getName().contains(hour +".history")){
                currentTimeFile.add(historyFile);
            }
        });
        return currentTimeFile;
    }

    public static List<DaGameEvent> readerHistoryLog(String path){
        List<File> all = readerAllHistoryLog(path);
        List<File> currentHistoryLog = getCurrentHistoryLog(all);
        List<DaGameEvent> historyLogReaderVos = new ArrayList<>();
        for (File file : currentHistoryLog){
            historyLogReaderVos.addAll(reader(file));
        }
        return historyLogReaderVos.stream().sorted(Comparator.comparing(DaGameEvent::getFileIndex)).toList();
    }

}
