package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import com.hyphenate.easeui.EaseConstant;

public class Constant extends EaseConstant {

    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String HX_CURRENT_USER_ID = "hx_current_user_id";

    public static final String HEAD_IMAGE_URL = "headImageUrl";//发送人的头像
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String SEX = "sex";
    public static final String OBJECT_HEAD_IMAGE_URL = "objectHeadImageUrl";//接收人的头像
    public static final String OBJECT_USERID = "objectUserid";
    public static final String OBJECT_USER_NAME = "objectUserName";
    public static final String OBJECT_USER_SEX = "objectUserSex";

    public static final String BASE_URL = "http://119.23.72.141:8080/t_user_app";
    /**
     * 获取验证码
     **/
    public static final String GETIDENTIFY = BASE_URL + "/getIdentify";
    /**
     * 登陆
     **/
    public static final String LOGIN = BASE_URL + "/login";
    /**
     * 第三方登陆
     **/
    public static final String THIRDLOGIN = BASE_URL + "/thirdLogin";
    /**
     * 注册
     **/
    public static final String REGISTER = BASE_URL + "/register";
    /**
     * 修改密码
     **/
    public static final String UPDATEPWD = BASE_URL + "/updatePwd";

    /**
     * 更新用户资料
     **/
    public static final String UPDATEUSER = BASE_URL + "/updateUser";
    /**
     * 查询咨询
     **/
    public static final String FINDINFORMATIONS = BASE_URL + "/findInformations";
    /**
     * 查询商品信息
     **/
    public static final String FINDCOMMODITYS = BASE_URL + "/findCommoditys";
    /**
     * 增加购物车信息
     **/
    public static final String ADDSHOPPINGCAR = BASE_URL + "/addShoppingCar";
    /**
     * 删除购物车信息
     **/
    public static final String DELETESHOPPINGCAR = BASE_URL + "/deleteShoppingCar";
    /**
     * 查询购物车信息
     **/
    public static final String FINDSHOPPINGCAR = BASE_URL + "/findShoppingCar";
    /**
     * 修改购物车信息
     **/
    public static final String UPDATESHOPPINGCAR = BASE_URL + "/updateShoppingCar";
    /**
     * 添加好友
     **/
    public static final String ADDFRIEND = BASE_URL + "/addFriend";
    /**
     * 查找好友
     **/
    public static final String FINDFRIENDS = BASE_URL + "/findFriends";
    /**
     * 添加订单
     **/
    public static final String INSERTORDER = BASE_URL + "/insertOrder";
    /**
     * 查询订单信息
     **/
    public static final String FINDORDER = BASE_URL + "/findOrder";
    /**
     * 查询所有订单
     **/
    public static final String FINDALLORDER = BASE_URL + "/findAllOrder";
    /**
     * 添加收货地址
     **/
    public static final String ADDADDRESS = BASE_URL + "/addAddress";
    /**
     * 删除收货地址
     **/
    public static final String DELETEADDRESS = BASE_URL + "/deleteAddress";
    /**
     * 查询收货地址
     **/
    public static final String FINDADDRESS = BASE_URL + "/findAddress";
    /**
     * 修改收货地址
     **/
    public static final String UPDATEADDRESS = BASE_URL + "/updateAddress";
    /**
     * 更改默认地址
     **/
    public static final String UPDATEADDRESSDEFAULT = BASE_URL + "/updateAddressDefault";
    /**
     * 投诉与建议
     **/
    public static final String INSERTSUGGEST = BASE_URL + "/insertSuggest";
    /**
     * 验证用户
     */
    public static final String FINDUSERROLE = BASE_URL + "/findUserRole";
    /**
     * 修改订单状态
     */
    public static final String UPDATEORDERSTATU = BASE_URL + "/updateOrderStatu";
    /**
     * 查询默认地址
     */
    public static final String FINDDEFAULTADD = BASE_URL + "/findDefaultAdd";
    /**
     * 添加退货信息
     */
    public static final String INSERTRETURNMSG = BASE_URL + "/insertReturnMsg";
    /**
     * 添加退货物流信息
     */
    public static final String INSERTLOGISTICSRETURNMSG = BASE_URL + "/insertLogisticsReturnMsg";
    /**
     * 获取订单信息
     */
    public static final String CREATECHARGE = BASE_URL + "/createCharge";
    /**
     * 更改加好友状态
     */
    public static final String UPDATEFRIENDSTATUS = BASE_URL + "/updateFriendStatus";
    /**
     * 加好友状态
     */
    public static final String ADDFRIENDREQUEST = BASE_URL + "/addFriendRequest";
    /**
     * 删除好友
     */
    public static final String DELFRIEND = BASE_URL + "/delFriend";
    /**
     * 查询物流
     */
    public static final String WULIU = "https://ali-deliver.showapi.com/showapi_expInfo";
}
