package com.xfrenzy47x.service;

import com.xfrenzy47x.model.CodePlatformPage;
import com.xfrenzy47x.repository.CodePlatformPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class CodePlatformPageService {

    Logger logger = Logger.getLogger(CodePlatformPageService.class.getName());

    @Autowired
    private CodePlatformPageRepository codePlatformPageRepository;

    public CodePlatformPage get(String id) {
        CodePlatformPage page = codePlatformPageRepository.findById(id).orElse(null);
        logger.log(Level.INFO, "get : {0}", id);
        logger.log(Level.INFO, "get : {0}", page);
        if (page != null && (page.getViewsRestricted() || page.getTimeRestricted())) {
            if (page.getViews() >= 0 && page.getViewsRestricted()) {
                page.setViews(page.getViews() - 1);
                page = codePlatformPageRepository.save(page);
                if (page.getViews() < 0) {
                    logger.log(Level.INFO, "get PageViews < 0 : {0}", page);
                    delete(page);
                    return null;
                }
            }
            if (page.getTime() > 0) {
                Long secondsPassed = Duration.between(page.getCreatedDate(), LocalDateTime.now()).getSeconds();
                page.setTime((int) (page.getOriginalTime() - secondsPassed));
                page = codePlatformPageRepository.save(page);
                if (page.getTime() <= 0) {
                    logger.log(Level.INFO, "get Time <= 0 : {0}", page);
                    delete(page);
                    return null;
                }
            }
        }

        return page;
    }

    public String add(String code, Integer time, Integer views) {
        CodePlatformPage page = codePlatformPageRepository.save(new CodePlatformPage(code, time, views));
        return page.getId();
    }

    public List<CodePlatformPage> getLatest() {
        List<CodePlatformPage> all = codePlatformPageRepository.findAll();
        return all.stream().filter(x -> !x.getViewsRestricted() && !x.getTimeRestricted()).sorted(Comparator.comparing(CodePlatformPage::getDate).reversed()).toList().subList(0, (Math.min(all.size(), 10)));
    }

    public void delete(CodePlatformPage page) {
        codePlatformPageRepository.delete(page);
    }
}
