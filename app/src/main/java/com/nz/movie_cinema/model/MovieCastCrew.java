package com.nz.movie_cinema.model;

import java.util.List;

public class MovieCastCrew {
    /**
     * id : 550
     * cast : [{"cast_id":4,"character":"The Narrator","credit_id":"52fe4250c3a36847f80149f3","gender":2,"id":819,"name":"Edward Norton","order":0,"profile_path":"/eIkFHNlfretLS1spAcIoihKUS62.jpg"},{"cast_id":5,"character":"Tyler Durden","credit_id":"52fe4250c3a36847f80149f7","gender":2,"id":287,"name":"Brad Pitt","order":1,"profile_path":"/kc3M04QQAuZ9woUvH3Ju5T7ZqG5.jpg"}]
     * crew : [{"credit_id":"56380f0cc3a3681b5c0200be","department":"Writing","gender":0,"id":7469,"job":"Screenplay","name":"Jim Uhls","profile_path":null},{"credit_id":"52fe4250c3a36847f8014a05","department":"Production","gender":0,"id":7474,"job":"Producer","name":"Ross Grayson Bell","profile_path":null}]
     */

    private int id;
    private List<CastBean> cast;
    private List<CrewBean> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CastBean> getCast() {
        return cast;
    }

    public void setCast(List<CastBean> cast) {
        this.cast = cast;
    }

    public List<CrewBean> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewBean> crew) {
        this.crew = crew;
    }

    public static class CastBean {
        /**
         * cast_id : 4
         * character : The Narrator
         * credit_id : 52fe4250c3a36847f80149f3
         * gender : 2
         * id : 819
         * name : Edward Norton
         * order : 0
         * profile_path : /eIkFHNlfretLS1spAcIoihKUS62.jpg
         */

        private int cast_id;
        private String character;
        private String credit_id;
        private int gender;
        private int id;
        private String name;
        private int order;
        private String profile_path;

        public int getCast_id() {
            return cast_id;
        }

        public void setCast_id(int cast_id) {
            this.cast_id = cast_id;
        }

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }

    public static class CrewBean {
        /**
         * credit_id : 56380f0cc3a3681b5c0200be
         * department : Writing
         * gender : 0
         * id : 7469
         * job : Screenplay
         * name : Jim Uhls
         * profile_path : null
         */

        private String credit_id;
        private String department;
        private int gender;
        private int id;
        private String job;
        private String name;
        private String profile_path;

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }
}
