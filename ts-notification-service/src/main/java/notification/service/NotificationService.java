package notification.service;

import notification.entity.NotifyInfo;
import org.springframework.http.HttpHeaders;

/**
 * @author Wenvi
 * @date 2017/6/15
 */
public interface NotificationService {

    /**
     * preserve success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean preserve_success(NotifyInfo info, HttpHeaders headers);

    /**
     * order create success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean order_create_success(NotifyInfo info, HttpHeaders headers);

    /**
     * order changed success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean order_changed_success(NotifyInfo info, HttpHeaders headers);

    /**
     * order cancel success with notify info
     *
     * @param info notify info
     * @param headers headers
     * @return boolean
     */
    boolean order_cancel_success(NotifyInfo info, HttpHeaders headers);
}
