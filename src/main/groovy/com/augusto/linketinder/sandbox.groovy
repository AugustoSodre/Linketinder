package com.augusto.linketinder

import com.augusto.linketinder.DAO.DAO

class sandbox {

    static void main(String[] args){
        DAO dao = new DAO()

        print(dao.listCompObject("empresa"))
    }
}
