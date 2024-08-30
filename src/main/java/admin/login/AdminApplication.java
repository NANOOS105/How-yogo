/*
 * @Controller
 * 
 * @SessionAttributes("username")
 * 
 * @SpringBootApplication public class AdminApplication {
 * 
 * // 관리자 계정 정보 (실제로는 데이터베이스에서 가져와야 함) private static final String
 * ADMIN_USERNAME = "admin12"; private static final String ADMIN_PASSWORD =
 * "admin12";
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(AdminApplication.class, args); }
 * 
 * @GetMapping("/") public String index(Model model) { if
 * (model.getAttribute("username") != null) { return "Hello, admin " +
 * model.getAttribute("username"); } return "Login required."; }
 * 
 * @GetMapping("/logout") public String logout(Model model) {
 * model.remove("username"); return "redirect:/"; } }
 */