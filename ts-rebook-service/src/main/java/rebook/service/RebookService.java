package rebook.service;

import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;

/**
 * Created by Administrator on 2017/6/26.
 */
public interface RebookService {
    RebookResult rebook(RebookInfo info, String loginId, String loginToken);
    RebookResult payDifference(RebookInfo info, String loginId, String loginToken);
}
