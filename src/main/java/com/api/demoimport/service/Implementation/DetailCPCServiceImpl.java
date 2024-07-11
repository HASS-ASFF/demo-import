package com.api.demoimport.service.Implementation;

import com.api.demoimport.entity.Bilan.DetailCPC;
import com.api.demoimport.entity.Bilan.Esg;
import com.api.demoimport.entity.Bilan.SubAccountCPC;
import com.api.demoimport.service.DetailCPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DetailCPCServiceImpl implements DetailCPCService {

    @Override
    public List<DetailCPC> convertCPCtoDetailCPC(List<SubAccountCPC> subAccountCPCS) {
        List<DetailCPC> CPCList = new ArrayList<>();
        for (SubAccountCPC val:subAccountCPCS){
            DetailCPC detailCPC = new DetailCPC();
            detailCPC.setN_compte(val.getN_compte());
            detailCPC.setName(val.getLibelle());
            detailCPC.setExercice(val.getBrut() != null ? val.getBrut() : 0.0);
            detailCPC.setExerciceP(0.0);
            CPCList.add(detailCPC);
        }
        return CPCList;
    }

    @Override
    public List<DetailCPC> processDataSix(List<SubAccountCPC> subAccountCPCS) {
        List<DetailCPC> mainAccountList;

        // CONVERSION
        List<DetailCPC> detailCPCList = convertCPCtoDetailCPC(subAccountCPCS);
        if(detailCPCList.isEmpty()){
            return null;
        }
        else {
            try{
                //initialize data with empty values
                mainAccountList = DetailCPC.initializeData("6");

                // loop after raw data values of balance
                for (DetailCPC result : detailCPCList) {
                    // Get the number account
                    String accountNumber = result.getN_compte();
                    String prefix = accountNumber.substring(0,4);
                    // Initialize total for each sub-account
                    Double total = 0.0;
                    //System.out.println(result.getBrut());
                    for (DetailCPC subAccount : mainAccountList) {
                        subAccount.setExerciceP(0.0);
                        // check for the same subAccount
                        if (subAccount.getName().startsWith(prefix) || subAccount.getName().contains(prefix)) {
                            // Adding the values
                            if(subAccount.getExercice() != null){
                                total = subAccount.getExercice() + (result.getExercice() == null ? 0.0:result.getExercice());
                            }else{
                                total = (result.getExercice() == null ? 0.0:result.getExercice());
                            }
                            subAccount.setN_compte(result.getN_compte());
                            subAccount.setExercice(total);
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to process data account cpc , error message : "+ e.getMessage());
            }
            return mainAccountList;
        }

    }

    @Override
    public List<DetailCPC> processDataSeven(List<SubAccountCPC> subAccountCPCS) {
        List<DetailCPC> mainAccountList;

        // CONVERSION
        List<DetailCPC> detailCPCList = convertCPCtoDetailCPC(subAccountCPCS);
        if(detailCPCList.isEmpty()){
            return null;
        }else {
            try{
                //initialize data with empty values
                mainAccountList = DetailCPC.initializeData("7");

                // loop after raw data values of balance
                for (DetailCPC result : detailCPCList) {
                    // Get the number account
                    String accountNumber = result.getN_compte();
                    String prefix = accountNumber.substring(0,3);
                    // Initialize total for each sub-account
                    Double total = 0.0;
                    //System.out.println(result.getBrut());
                    for (DetailCPC subAccount : mainAccountList) {
                        // check for the same subAccount
                        subAccount.setExerciceP(0.0);
                        if (subAccount.getName().startsWith(prefix) || subAccount.getName().contains(prefix)) {
                            // Adding the values
                            if(subAccount.getExercice() != null){
                                total = subAccount.getExercice() + (result.getExercice() == null ? 0.0:result.getExercice());
                            }else{
                                total = (result.getExercice() == null ? 0.0:result.getExercice());
                            }
                            subAccount.setN_compte(result.getN_compte());
                            subAccount.setExercice(total);
                            break;
                        }

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to process data account cpc , error message : "+ e.getMessage());
            }
            return mainAccountList;
        }

    }

    @Override
    public List<DetailCPC> FilterAccountDataDetailCPC(List<DetailCPC> detailCPCList, String mainAccount) {
        List<DetailCPC> filteredList = new ArrayList<>();
        for(DetailCPC val : detailCPCList){

            String [] parties = val.getName().split("[-]");
            if (parties.length > 1) {
                val.setName(parties[1].trim());
            }

            if(Objects.equals(val.getMain_account(), mainAccount)){
                filteredList.add(val);
            }
        }
        return filteredList;
    }

    @Override
    public Double GetTotalDataDetailCPCC(List<DetailCPC> data) {
        Double total = 0.0;
        for(DetailCPC val:data){
            total += val.getExercice();
        }
        return total;
    }

}
