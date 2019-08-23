package xyz.n490808114.train.domain;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

public class Employee implements Serializable {
    @Null private Integer id;
    @Valid private Dept dept;
    @Valid private Job job;
    @NotNull private String name;
    @Size(min = 18,max = 18) private String cardId;
    private String address;
    private String postCode;
    private String tel;
    @Size(min = 11,max = 11) private String phone;
    private String qqNum;
    @Email private String email;
    private Integer sex;
    private String party;
    @PastOrPresent private Date birthday;
    private String race;
    private String education;
    private String speciality;
    private String hobby;
    private String remark;
    @PastOrPresent private Date createDate;
    private static Map<String,String> sqlMapping = new HashMap<>();
    static{
        sqlMapping.put("id", "id");
        sqlMapping.put("dept", "dept_id");
        sqlMapping.put("job", "job_id");
        sqlMapping.put("name", "name");
        sqlMapping.put("cardId", "card_id");
        sqlMapping.put("address", "address");
        sqlMapping.put("postCode", "post_code");
        sqlMapping.put("tel", "tel");
        sqlMapping.put("phone", "phone");
        sqlMapping.put("qqNum", "qq_num");
        sqlMapping.put("email", "email");
        sqlMapping.put("sex", "sex");
        sqlMapping.put("party", "party");
        sqlMapping.put("birthday", "birthday");
        sqlMapping.put("race", "race");
        sqlMapping.put("education", "education");
        sqlMapping.put("speciality", "speciality");
        sqlMapping.put("hobby", "hobby");
        sqlMapping.put("remark", "remark");
        sqlMapping.put("createDate", "create_date");
    }
    public Employee(){
    }
    public Employee(int id,Dept dept,Job job,String name,String cardId,String address,String postCode,String tel,
                    String phone,String qqNum, String email,int sex,String party,Date birthday,String race,
                    String education, String speciality, String hobby,String remark,Date createDate){
        this.id=id;
        this.dept=dept;
        this.job=job;
        this.name=name;
        this.cardId=cardId;
        this.address=address;
        this.postCode=postCode;
        this.tel=tel;
        this.phone=phone;
        this.qqNum=qqNum;
        this.email=email;
        this.sex=sex;
        this.party=party;
        this.birthday=birthday;
        this.race=race;
        this.education=education;
        this.speciality=speciality;
        this.hobby=hobby;
        this.remark=remark;
        this.createDate=createDate;
    }
    public Employee(Map<String,String> map){
        for(String key : map.keySet()){
            switch (key){
                case "id":this.id = Integer.parseInt(map.get("id"));break;
                case "dept":this.dept = new Dept(Integer.parseInt(map.get("dept")));break;
                case "job":this.job = new Job(Integer.parseInt(map.get("job")));break;
                case "name":this.name = map.get("name");break;
                case "sex":this.sex = Integer.parseInt(map.get("sex"));break;
                case "cardId":this.cardId = map.get("cardId");break;
                case "address":this.address = map.get("address");break;
                case "postCode":this.postCode = map.get("postCode");break;
                case "tel":this.tel= map.get("tel");break;
                case "phone":this.phone = map.get("phone");break;
                case "qqNum":this.qqNum = map.get("qqNum");break;
                case "email":this.email = map.get("email");break;
                case "party":this.party = map.get("party");break;
                case "birthday":this.birthday = new Date(Long.parseLong(map.get("birthday")));break;
                case "race":this.race = map.get("race");break;
                case "education":this.education = map.get("education");break;
                case "speciality":this.speciality = map.get("speciality");break;
                case "hobby":this.hobby = map.get("hobby");break;
                case "remark":this.remark = map.get("remark");break;
                case "createDate":this.createDate = new Date(Long.parseLong(map.get("createDate")));break;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }


    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @param sqlMapping the sqlMapping to set
     */
    public static void setSqlMapping(Map<String, String> sqlMapping) {
        Employee.sqlMapping = sqlMapping;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Dept getDept() {
        return dept;
    }

    public Job getJob() {
        return job;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getRemark() {
        return remark;
    }


    public String getAddress() {
        return address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCardId() {
        return cardId;
    }

    public String getEmail() {
        return email;
    }

    public String getParty() {
        return party;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEducation() {
        return education;
    }

    public String getQqNum() {
        return qqNum;
    }

    public String getHobby() {
        return hobby;
    }

    public String getRace() {
        return race;
    }

    public String getTel() {
        return tel;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
    /**
     * @param sex the sex to set
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    /**
     * @return the sex
     */
    public Integer getSex() {
        return sex;
    }
    /**
     * @return the sqlMapping
     */
    public static Map<String, String> getSqlMapping() {
        return sqlMapping;
    }

    @Override
    public String toString() {
        return "Employee [id="+id+
                ",dept="+dept.getId()+
                ",job="+job.getId()+
                ",name="+name+
                ",cardId="+cardId+
                ",address="+address+
                ",postCard="+postCode+
                ",tel="+tel+
                ",phone="+phone+
                ",qqNum="+qqNum+
                ",email="+email+
                ",sex="+sex+
                ",party="+party+
                ",birthday="+birthday+
                ",race="+race+
                ",education="+education+
                ",speciality"+speciality+
                ",hobby="+hobby+
                ",remark="+remark+
                ",createDate="+createDate +"]";
    }

}
