package plus.easydo.dnf.constant;

/**
 * @author laoyu
 * @version 1.0
 * @description 系统常量
 * @date 2023/11/22
 */

public class SystemConstant {

    public static final String PEM_PATH = "da_pem_path";
    public static final String PVF_PATH = "da_pvf_path";
    public static final String READER_PVF = "gm_reader_pvf";
    public static final String RESTART_SERVER = "da_restart_server";
    public static final String RESTART_DB = "da_restart_db";
    public static final String RESTART_DA = "da_restart_da";
    public static final String RSA_PRIVATE_KEY = "PRIVATE KEY";
    public static final String RSA_PUBLIC_KEY = "PUBLIC KEY";
    public static final String PRIVATE_KEY_PEM = "privatekey.pem";
    public static final String PUBLIC_KEY_PEM = "publickey.pem";
    public static final String FRIDA_JS_PATH = "frida_js_path";
    public static final String FRIDA_JSON_PATH = "frida_json_path";
    public static final String FRIDA_JSON_DEFAULT_VALUE = "frida_json_default_value";
    public static final String MAIN_PYTHON_PATH = "main_python_path";
    public static final String GET_PID_PS_CMD = "get_pid_ps_cmd";
    public static final String GET_SERVER_IP_CMD = "get_server_ip_cmd";
    public static final String GET_DA_IP_CMD = "get_da_ip_cmd";
    public static final String START_FRIDA_CLIENT_CMD = "start_frida_client_cmd";
    public static final String STOP_CONTAINER_CMD = "stop_container_cmd";
    public static final String REMOVE_CONTAINER_CMD = "remove_container_cmd";
    public static final String GET_CONTAINER_LIST_ALL_CMD = "get_container_list_all_cmd";
    public static final String GET_CONTAINER_LIST_RUN_CMD = "get_container_list_run_cmd";
    public static final String DA_WSS_ADDRESS = "da_wss_address";
    public static final String DOCKER_INSPECT_CMD = "docker_inspect_cmd";
    public static final String DOCKER_LOG_CMD = "docker_log_cmd";
    public static final String INTRODUCE_FUNCTIONS_BEGIN = "introduce_functions_begin";
    public static final String INTRODUCE_FUNCTIONS_END = "introduce_functions_end";
    public static final String TEMPORARY_INTRODUCE_FUNCTIONS_BEGIN = "temporary_introduce_functions_begin";
    public static final String TEMPORARY_INTRODUCE_FUNCTIONS_END = "temporary_introduce_functions_end";
    public static final String FUNCTION_CONTEXT_BEGIN = "function_context_begin";
    public static final String FUNCTION_CONTEXT_END = "function_context_end";
    public static final String GAME_USER_LOGIN = "game_user_login";
    public static final String GAME_USER_EXIT = "game_user_exit";
    public static final String CAPTCHA_KEY = "captcha_key";
    public static final String MYSQL_DUMP_PATH = "/data/mysql/da_backup.sql";
    public static final String MYSQL_DUMP_SH_TEXT = "mysqldump -u %s -p%s --all-databases > /var/lib/mysql/da_backup.sql";
    public static final String MYSQL_DUMP_SH_PATH = "/data/mysql/backup.sh";
    public static final String MYSQL_DUMP_CMD = "docker exec dnfmysql sh /var/lib/mysql/backup.sh";
    public static final String MYSQL_IMPORT_SH_PATH = "/data/mysql/import.sh";
    public static final String MYSQL_IMPORT_CMD = "docker exec dnfmysql mysql -u %s -p%s < /var/lib/mysql/da_backup.sql";
    public static final String OPEN_MEMBER_DUNGEON_LEVEL = "1|3,2|3,3|3,4|3,5|3,6|3,7|3,8|3,9|3,11|3,12|3,13|3,14|3,15|3,16|1,17|3,21|3,22|3,23|3,24|3,25|3,26|3,27|3,31|3,32|3,33|3,34|3,35|3,36|3,37|3,40|3,41|2,42|3,43|3,44|3,45|3,50|3,51|3,52|3,53|3,60|3,61|3,62|2,63|3,64|3,65|3,67|3,70|3,71|3,72|3,73|3,74|3,75|3,76|3,77|3,80|3,81|3,82|3,83|3,84|3,85|3,86|3,87|3,88|3,89|3,90|3,91|2,92|3,93|3,100|3,101|3,102|3,103|3,104|3,110|3,111|3,112|3,140|3,141|3,502|3,511|3,515|1,518|1,521|3,1000|3,1500|3,1501|3,1502|3,1507|1,3506|3,10000|3";
    public static final String LOG_PATH = "/data/server/log";



    private SystemConstant() {
    }

}
