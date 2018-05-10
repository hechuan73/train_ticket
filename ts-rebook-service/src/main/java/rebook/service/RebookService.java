package rebook.service;

import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;


public interface RebookService {
    RebookResult rebook(RebookInfo info, String loginId, String loginToken);
    RebookResult payDifference(RebookInfo info, String loginId, String loginToken);
}
