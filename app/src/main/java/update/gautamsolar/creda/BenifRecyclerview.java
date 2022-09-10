package update.gautamsolar.creda;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import update.gautamsolar.creda.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BenifRecyclerview extends RecyclerView.Adapter<BenifRecyclerview.Benifviewholder> implements Filterable, View.OnClickListener {


    //this context we will use to inflate the layout
    private Context mCtx;
    SharedPreferences settings;
    String sregnnumber, sbenifname, sfname, scontact, svillage, sblock, spumpType, sinstallation_status, sbeneficiary_share, ssurvey_status, sfoundation_status;
    String spump_type, sdispatch_status, spump_capacity, sengineer_role, sKEYPHOTO1, sKEYPHOTO2, sKEYPHOTO3, sKEYPHOTO4, sKEYPHOTO5, sKEYPHOTO6, sKEYPHOTO7, sFKEYPHOTO1, sFKEYPHOTO2, sFKEYPHOTO3, sFKEYPHOTO4, sFKEYPHOTO5, sSKEYPHOTO1, sSKEYPHOTO2, sSKEYPHOTO3, sSKEYPHOTO4;
    String water_level,bor_size,bor_depth,existing_moter_run,old_rmu, regnnumber, benifname, fname, contact, village, block, pumpType, installation_status, beneficiary_share, survey_status, foundation_status;
    String pump_type, dispatch_status, pump_capacity, engineer_role, KEYPHOTO1, KEYPHOTO2, KEYPHOTO3, KEYPHOTO4, KEYPHOTO5, KEYPHOTO6, KEYPHOTO7, FKEYPHOTO1,
            FKEYPHOTO2, FKEYPHOTO3, FKEYPHOTO4, FKEYPHOTO5, FKEYPHOTO6, FKEYPHOTO7, SKEYPHOTO1, SKEYPHOTO2,
            SKEYPHOTO3, SKEYPHOTO4,rmu_status;


    //we are storing all the products in a list
    private List<CredaModel> credamodellist, filterList;

    //getting the context and product list with constructor
    public BenifRecyclerview(List<CredaModel> credamodellist, Context mCtx) {
        this.mCtx = mCtx;
        this.credamodellist = credamodellist;

        this.filterList = new ArrayList<CredaModel>();
        // we copy the original list to the filter list and use it for setting row values
        this.filterList.addAll(this.credamodellist);
    }

    @Override
    public Benifviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.beniflistitem, null);
        return new Benifviewholder(view);
    }

    @Override
    public void onBindViewHolder(Benifviewholder holder, final int position) {
        //getting the product of the specified position
//        final CredaModel list_model = filterList.get(position);
        settings = PreferenceManager.getDefaultSharedPreferences(mCtx);
        String project = settings.getString("project", "");

        if (project.equals("RAJASTHAN")) {
            final CredaModel credaModel = filterList.get(position);


            //binding the data with the viewholder views
            holder.Benifname.setText(credaModel.getBenifname());
            holder.Regnnumber.setText(credaModel.getRegistrationno());
            holder.Fname.setText(credaModel.getFname());
            holder.contactno.setText(credaModel.getContact());
            holder.village.setText(credaModel.getVillage());
            holder.pumptype.setText(credaModel.getPumptype());
            holder.district.setText(credaModel.getDistrict());
            holder.block.setText(credaModel.getBlock());
            holder.OLD_RMU.setText(credaModel.getOld_rmu_id());
            holder.sceme.setText(credaModel.getPhase());

            holder.Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    settings = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    regnnumber = credaModel.getRegistrationno();
                    old_rmu = credaModel.getOld_rmu_id();
                    water_level = credaModel.getWater_level();
                    bor_size = credaModel.getBor_size();
                    bor_depth = credaModel.getBor_depth();
                    existing_moter_run = credaModel.getExisting_motor_run();
                    benifname = credaModel.getBenifname();
                    fname = credaModel.getFname();
                    contact = credaModel.getContact();
                    village = credaModel.getVillage();
                    block = credaModel.getBlock();
                    pumpType = credaModel.getPumptype();
                    installation_status = credaModel.getInstalletion_status();
                    survey_status = credaModel.getSitesurvey();
                    foundation_status = credaModel.getFoundation_status();
                    dispatch_status = credaModel.getDipatch_status();
                    pump_capacity = credaModel.getPump_capacity();
                    KEYPHOTO1 = credaModel.getInst_image1();
                    KEYPHOTO2 = credaModel.getInst_image2();
                    KEYPHOTO3 = credaModel.getInst_image3();
                    KEYPHOTO4 = credaModel.getInst_image4();
                    FKEYPHOTO1 = credaModel.getFound_image1();
                    FKEYPHOTO2 = credaModel.getFound_image2();
                    FKEYPHOTO3 = credaModel.getFound_image3();
                    FKEYPHOTO4 = credaModel.getFound_image4();
                    SKEYPHOTO1 = credaModel.getAdhar_image();
                    SKEYPHOTO2 = credaModel.getPassbook_image();
                    SKEYPHOTO3 = credaModel.getSidemark_image();
                    SKEYPHOTO4 = credaModel.getBenificiary_image();
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("regnnumber", regnnumber);
                    editor.putString("old_rmu", old_rmu);
                    editor.putString("benifname", benifname);
                    editor.putString("fname", fname);
                    editor.putString("contact", contact);
                    editor.putString("block", block);
                    editor.putString("village", village);
                    editor.putString("pumpType", pumpType);
                    editor.putString("installation_status", installation_status);
                    editor.putString("pump_capacity", pump_capacity);
                    editor.putString("foundation_status", foundation_status);
                    editor.putString("survey_status", survey_status);
                    editor.putString("installation_status", installation_status);
                    editor.putString("dispatch_status", dispatch_status);
                    editor.putString("KEYPHOTO1", KEYPHOTO1);
                    editor.putString("KEYPHOTO2", KEYPHOTO2);
                    editor.putString("KEYPHOTO3", KEYPHOTO3);
                    editor.putString("KEYPHOTO4", KEYPHOTO4);
                    editor.putString("FKEYPHOTO1", FKEYPHOTO1);
                    editor.putString("bor_status",credaModel.getBore_Status());
                    editor.putString("FKEYPHOTO2", FKEYPHOTO2);
                    editor.putString("FKEYPHOTO3", FKEYPHOTO3);
                    editor.putString("FKEYPHOTO4", FKEYPHOTO4);
                    editor.putString("SKEYPHOTO1", SKEYPHOTO1);
                    editor.putString("SKEYPHOTO2", SKEYPHOTO2);
                    editor.putString("SKEYPHOTO3", SKEYPHOTO3);
                    editor.putString("SKEYPHOTO4", SKEYPHOTO4);
                    editor.putString("water_level", water_level);
                    editor.putString("bor_size", bor_size);
                    editor.putString("bor_depth", bor_depth);
                    editor.putString("existing_moter_run", existing_moter_run);
                    editor.putString("rmu_no",credaModel.getRmu_number());
                    editor.commit();

                    Intent i = new Intent(v.getContext(), UploadAll.class);

//                    i.putExtra("Benifname", credaModel.getBenifname());
//                    i.putExtra("Regnnumber", credaModel.getRegistrationno());
//                    i.putExtra("Fname", credaModel.getFname());
//                    i.putExtra("contactno", credaModel.getContact());
//                    i.putExtra("village", credaModel.getVillage());
//                    i.putExtra("district", credaModel.getDistrict());
//                    i.putExtra("block", credaModel.getBlock());
//                    i.putExtra("pumptype", credaModel.getPumptype());
//                    i.putExtra("instimg1", credaModel.getInst_image1());
//                    i.putExtra("instimg2", credaModel.getInst_image2());
//                    i.putExtra("instimg3", credaModel.getInst_image3());
//                    i.putExtra("instimg4", credaModel.getInst_image4());
//                    i.putExtra("fondimg1", credaModel.getFound_image1());
//                    i.putExtra("fondimg2", credaModel.getFound_image2());
//                    i.putExtra("fondimg3", credaModel.getFound_image3());
//                    i.putExtra("fondimg4", credaModel.getFound_image4());
//                    i.putExtra("adharimg", credaModel.getAdhar_image());
//
//                    i.putExtra("passbookimg", credaModel.getPassbook_image());
//                    i.putExtra("sidemarkimg", credaModel.getSidemark_image());
//                    i.putExtra("benifimg", credaModel.getBenificiary_image());
//                    i.putExtra("installation_status", credaModel.getInstalletion_status());
//                    i.putExtra("survey_status", credaModel.getSitesurvey());
//                    i.putExtra("foundation_status", credaModel.getFoundation_status());
//                    i.putExtra("pump_capacity", credaModel.getPump_capacity());
//                    i.putExtra("existing_moter_run", credaModel.getExisting_motor_run());
//                    i.putExtra("bor_depth", credaModel.getBor_depth());
//                    i.putExtra("bor_size",credaModel.getBor_size());
//                    i.putExtra("water_level",credaModel.getWater_level());

                    mCtx.startActivity(i);

                   }
            });


        }

        else if (project.equals("MP")) {
            final CredaModel credaModel = filterList.get(position);
            //binding the data with the viewholder views
            holder.Benifname.setText(credaModel.getBenifname());
            holder.Regnnumber.setText(credaModel.getRegistrationno());
            holder.Fname.setText(credaModel.getFname());
            holder.contactno.setText(credaModel.getContact());
            holder.village.setText(credaModel.getVillage());
            holder.pumptype.setText(credaModel.getPumptype());
            holder.district.setText(credaModel.getDistrict());
            holder.block.setText(credaModel.getBlock());
            holder.OLD_RMU.setText(credaModel.getOld_rmu_id());
            holder.sceme.setText(credaModel.getPhase());
            holder.Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    final CredaModel credaModel = credamodellist.get(position);

                    Intent i = new Intent(v.getContext(), UploadAll.class);
                    regnnumber = credaModel.getRegistrationno();
                    old_rmu = credaModel.getOld_rmu_id();
                    water_level = credaModel.getWater_level();
                    bor_size = credaModel.getBor_size();
                    bor_depth = credaModel.getBor_depth();
                    existing_moter_run = credaModel.getExisting_motor_run();
                    benifname = credaModel.getBenifname();
                    fname = credaModel.getFname();
                    contact = credaModel.getContact();
                    village = credaModel.getVillage();
                    block = credaModel.getBlock();
                    pumpType = credaModel.getPumptype();
                    installation_status = credaModel.getInstalletion_status();
                    survey_status = credaModel.getSitesurvey();
                    foundation_status = credaModel.getFoundation_status();
                    dispatch_status = credaModel.getDipatch_status();
                    pump_capacity = credaModel.getPump_capacity();
                    KEYPHOTO1 = credaModel.getInst_image1();
                    KEYPHOTO2 = credaModel.getInst_image2();
                    KEYPHOTO3 = credaModel.getInst_image3();
                    KEYPHOTO4 = credaModel.getInst_image4();
                    FKEYPHOTO1 = credaModel.getFound_image1();
                    FKEYPHOTO2 = credaModel.getFound_image2();
                    FKEYPHOTO3 = credaModel.getFound_image3();
                    FKEYPHOTO4 = credaModel.getFound_image4();
                    FKEYPHOTO5 = credaModel.getFound_image5();
                    FKEYPHOTO6 = credaModel.getFound_image6();
                    FKEYPHOTO7 = credaModel.getFound_imiage7();
                    SKEYPHOTO1 = credaModel.getAdhar_image();
                    SKEYPHOTO2 = credaModel.getPassbook_image();
                    SKEYPHOTO3 = credaModel.getSidemark_image();
                    SKEYPHOTO4 = credaModel.getBenificiary_image();
                    rmu_status=credaModel.getRmu_status();
                    settings = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("regnnumber", regnnumber);
                     editor.putString("rmu_status", rmu_status);
                    editor.putString("old_rmu", old_rmu);
                    editor.putString("benifname", benifname);
                    editor.putString("fname", fname);
                    editor.putString("contact", contact);
                    editor.putString("block", block);
                    editor.putString("village", village);
                    editor.putString("pumpType", pumpType);
                    editor.putString("installation_status", installation_status);
                    editor.putString("pump_capacity", pump_capacity);
                    editor.putString("foundation_status", foundation_status);
                    editor.putString("survey_status", survey_status);
                    editor.putString("installation_status", installation_status);
                    editor.putString("dispatch_status", dispatch_status);
                    editor.putString("KEYPHOTO1", KEYPHOTO1);
                    editor.putString("KEYPHOTO2", KEYPHOTO2);
                    editor.putString("KEYPHOTO3", KEYPHOTO3);
                    editor.putString("KEYPHOTO4", KEYPHOTO4);
                    editor.putString("FKEYPHOTO1", FKEYPHOTO1);
                    editor.putString("FKEYPHOTO2", FKEYPHOTO2);
                    editor.putString("FKEYPHOTO3", FKEYPHOTO3);
                    editor.putString("FKEYPHOTO4", FKEYPHOTO4);
                    editor.putString("FKEYPHOTO5", FKEYPHOTO5);
                    editor.putString("FKEYPHOTO6", FKEYPHOTO6);
                    editor.putString("FKEYPHOTO7", FKEYPHOTO7);
                    editor.putString("SKEYPHOTO1", SKEYPHOTO1);
                    editor.putString("SKEYPHOTO2", SKEYPHOTO2);
                    editor.putString("SKEYPHOTO3", SKEYPHOTO3);
                    editor.putString("SKEYPHOTO4", SKEYPHOTO4);
                    editor.putString("existing_moter_run", credaModel.getExisting_motor_run());
                    editor.putString("bor_depth", credaModel.getBor_depth());
                    editor.putString("bor_size",credaModel.getBor_size());
                    editor.putString("bor_status",credaModel.getBore_Status());
                    editor.putString("water_level",credaModel.getWater_level());
                    editor.putString("adhar_no", credaModel.getAdhar_number());
                    editor.putString("bank_name",credaModel.getBank_name());
                    editor.putString("bank_account_no",credaModel.getAccount_number());
                    editor.putString("ifsc_code",credaModel.getIfsc_code());
                    editor.putString("pump_head",credaModel.getPumphead());
                    editor.putString("CMC_1", credaModel.getCMC_1());
                    editor.putString("CMC_2", credaModel.getCMC_2());
                    editor.putString("CMC_3", credaModel.getCMC_3());
                //    Toast.makeText(mCtx,regnnumber,Toast.LENGTH_LONG).show();
                    editor.putString("CMC_4", credaModel.getCMC_4());
                    editor.putString("CMC_5", credaModel.getCMC_5());
                    editor.putString("CMC_6", credaModel.getCMC_6());
                    editor.putString("CMC_7", credaModel.getCMC_7());
                    editor.putString("CMC_8", credaModel.getCMC_8());
                    editor.putString("CMC_9", credaModel.getCMC_9());
                    editor.putString("CMC_10", credaModel.getCMC_10());
                    editor.putString("rmu_no",credaModel.getRmu_number());
                    editor.commit();
                    mCtx.startActivity(i);
//                    i.putExtra("Benifname", credaModel.getBenifname());
//                    i.putExtra("Regnnumber", credaModel.getRegistrationno());
//                    i.putExtra("Fname", credaModel.getFname());
//                    i.putExtra("contactno", credaModel.getContact());
//                    i.putExtra("village", credaModel.getVillage());
//                    i.putExtra("district", credaModel.getDistrict());
//                    i.putExtra("block", credaModel.getBlock());
//                    i.putExtra("pumptype", credaModel.getPumptype());
//                    i.putExtra("instimg1", credaModel.getInst_image1());
//                    i.putExtra("instimg2", credaModel.getInst_image2());
//                    i.putExtra("instimg3", credaModel.getInst_image3());
//                    i.putExtra("instimg4", credaModel.getInst_image4());
//                    i.putExtra("fondimg1", credaModel.getFound_image1());
//                    i.putExtra("fondimg2", credaModel.getFound_image2());
//                    i.putExtra("fondimg3", credaModel.getFound_image3());
//                    i.putExtra("fondimg4", credaModel.getFound_image4());
//                    i.putExtra("fondimg5", credaModel.getFound_image5());
//                    i.putExtra("foundimg6", credaModel.getFound_image6());
//                    i.putExtra("foundimg7", credaModel.getFound_imiage7());
//                    i.putExtra("adharimg", credaModel.getAdhar_image());
//                    i.putExtra("passbookimg", credaModel.getPassbook_image());
//                    i.putExtra("sidemarkimg", credaModel.getSidemark_image());
//                    i.putExtra("benifimg", credaModel.getBenificiary_image());
//                    i.putExtra("installation_status", credaModel.getInstalletion_status());
//                    i.putExtra("survey_status", credaModel.getSitesurvey());
//                    i.putExtra("foundation_status", credaModel.getFoundation_status());
//                    i.putExtra("pump_capacity", credaModel.getPump_capacity());
//
//



                }
            });


        }

        else if (project.equals("CREDA")||project.equals("HAREDA")||project.equals("MSKPY")||project.equals("PEDA")||project.equals("MSEDCL")||project.equals("MEDA")) {
            final CredaModel credaModel = filterList.get(position);

            if(project.equals("HAREDA")){
                holder.upload.setVisibility(View.VISIBLE);
                holder.download.setVisibility(View.VISIBLE);
                holder.upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent i = new Intent(v.getContext(),VideoCaptureActivity.class);
                        i.putExtra("regnnumber",credaModel.getRegistrationno());
                        v.getContext().startActivity(i);
                    }
                });
                holder.download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(credaModel.getInstallation_video().equals("null")||credaModel.getInstallation_video().equals("")){
                            Toast.makeText(mCtx, "Video Not Found Please Upload", Toast.LENGTH_SHORT).show();
                        }else{
                        Uri uri= Uri.parse( credaModel.getInstallation_video());
                        DownloadManager downloadManager = (DownloadManager)v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE);

                        request.setTitle("Video Downloading..");
                        request.setDescription("video download for "+credaModel.getBenifname());

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                       request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,credaModel.getRegistrationno()+".mp4");
                        request.setMimeType("*/*");
                        downloadManager.enqueue(request);
                    }}
                });
            }


            //binding the data with the viewholder views
            holder.Benifname.setText(credaModel.getBenifname());
            holder.Regnnumber.setText(credaModel.getRegistrationno());
            holder.Fname.setText(credaModel.getFname());
            holder.contactno.setText(credaModel.getContact());
            holder.village.setText(credaModel.getVillage());
            holder.pumptype.setText(credaModel.getPumptype());
            holder.district.setText(credaModel.getDistrict());
            holder.block.setText(credaModel.getBlock());
            holder.OLD_RMU.setText(credaModel.getOld_rmu_id());
            holder.sceme.setText(credaModel.getPhase());
            if(TextUtils.isEmpty(credaModel.getMedaReg())){
                holder.benifIdLayout.setVisibility(View.GONE);
            }else{
                holder.benifIdLayout.setVisibility(View.VISIBLE);
            }
            holder.meda_reg.setText(credaModel.getMedaReg());
            holder.meda_reg.setSelected(true);
            holder.meda_reg.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.meda_reg.setSingleLine(true);
            holder.Regnnumber.setSelected(true);
            holder.Regnnumber.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            holder.Regnnumber.setSingleLine(true);

            holder.Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                CredaModel credaModel = credamodellist.get(position);

                    Intent i = new Intent(v.getContext(), UploadAll.class);
                    settings = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    regnnumber = credaModel.getRegistrationno();

                 //   Toast.makeText(mCtx,regnnumber,Toast.LENGTH_LONG).show();
                    old_rmu = credaModel.getOld_rmu_id();
                    benifname = credaModel.getBenifname();
                    fname = credaModel.getFname();
                    contact = credaModel.getContact();
                    village = credaModel.getVillage();
                    block = credaModel.getBlock();
                    pumpType = credaModel.getPumptype();
                    installation_status = credaModel.getInstalletion_status();
                    survey_status = credaModel.getSitesurvey();
                    foundation_status = credaModel.getFoundation_status();
                    dispatch_status = credaModel.getDipatch_status();
                    pump_capacity = credaModel.getPump_capacity();
                    KEYPHOTO1 = credaModel.getInst_image1();
                    KEYPHOTO2 = credaModel.getInst_image2();
                    KEYPHOTO3 = credaModel.getInst_image3();
                    KEYPHOTO4 = credaModel.getInst_image4();
                    KEYPHOTO5 = credaModel.getInst_image5();
                    KEYPHOTO6 = credaModel.getInst_image6();
                    KEYPHOTO7 = credaModel.getInst_image7();
                    FKEYPHOTO1 = credaModel.getFound_image1();
                    FKEYPHOTO2 = credaModel.getFound_image2();
                    FKEYPHOTO3 = credaModel.getFound_image3();
                    FKEYPHOTO4 = credaModel.getFound_image4();
                    FKEYPHOTO5 = credaModel.getFound_image5();
                    SKEYPHOTO1 = credaModel.getAdhar_image();
                    SKEYPHOTO2 = credaModel.getPassbook_image();
                    SKEYPHOTO3 = credaModel.getSidemark_image();
                    SKEYPHOTO4 = credaModel.getBenificiary_image();
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("regnnumber", regnnumber);
                    editor.putString("old_rmu", old_rmu);
                    editor.putString("benifname", benifname);
                    editor.putString("fname", fname);
                    editor.putString("contact", contact);
                    editor.putString("block", block);
                    editor.putString("village", village);
                    editor.putString("pumpType", pumpType);
                    editor.putString("installation_status", installation_status);
                    editor.putString("pump_capacity", pump_capacity);
                    editor.putString("foundation_status", foundation_status);
                    editor.putString("survey_status", survey_status);
                    editor.putString("installation_status", installation_status);
                    editor.putString("dispatch_status", dispatch_status);
                    editor.putString("KEYPHOTO1", KEYPHOTO1);
                    editor.putString("KEYPHOTO2", KEYPHOTO2);
                    editor.putString("KEYPHOTO3", KEYPHOTO3);
                    editor.putString("bor_status",credaModel.getBore_Status());
                    editor.putString("KEYPHOTO4", KEYPHOTO4);
                    editor.putString("KEYPHOTO5", KEYPHOTO5);
                    editor.putString("KEYPHOTO6", KEYPHOTO6);
                    editor.putString("KEYPHOTO7", KEYPHOTO7);
                    editor.putString("FKEYPHOTO1", FKEYPHOTO1);
                    editor.putString("FKEYPHOTO2", FKEYPHOTO2);
                    editor.putString("FKEYPHOTO3", FKEYPHOTO3);
                    editor.putString("FKEYPHOTO4", FKEYPHOTO4);
                    editor.putString("FKEYPHOTO5", FKEYPHOTO5);
                    editor.putString("SKEYPHOTO1", SKEYPHOTO1);
                    editor.putString("SKEYPHOTO2", SKEYPHOTO2);
                    editor.putString("SKEYPHOTO3", SKEYPHOTO3);
                    editor.putString("SKEYPHOTO4", SKEYPHOTO4);
                    editor.putString("existing_moter_run", credaModel.getExisting_motor_run());
                    editor.putString("bor_depth", credaModel.getBor_depth());
                    editor.putString("bor_size",credaModel.getBor_size());
                    editor.putString("water_level",credaModel.getWater_level());
                    editor.putString("rmu_no",credaModel.getRmu_number());
                    editor.putString("pit_status",credaModel.getPit_status());
                    editor.putString("SKEYPHOTO5",credaModel.getSite_format());
                    editor.putString("SKEYPHOTO6",credaModel.getAadhar_back());
                    editor.putString("SKEYPHOTO7",credaModel.getBoaring());
                    editor.putString("SKEYPHOTO8",credaModel.getSurvay2());
                    editor.putString("rate",credaModel.getRate_gitti_status());
                    editor.putString("road",credaModel.getRoad_status());
                    editor.putString("saria",credaModel.getSaria_status());
                    editor.putString("saralid",credaModel.getSaralid());
                    editor.putString("saralyear",credaModel.getSaralyear());
                    editor.putString("pic_date",credaModel.getPic_date());
                    editor.putString("lead_phase",credaModel.getPhase());
                    editor.putString("site_lat_new",credaModel.getSite_lat_new());
                    editor.putString("site_long_new",credaModel.getSite_long_new());
                    editor.putString("peda_image1",credaModel.getPeda_image1());
                    editor.putString("peda_image2",credaModel.getPeda_image2());
                    editor.putString("peda_image3",credaModel.getPeda_image3());
                    editor.putString("peda_image4",credaModel.getPeda_image4());
                    editor.putString("peda_image5",credaModel.getPeda_image5());

                editor.putString("site_video",credaModel.getSiteVideo());
                editor.putString("Consent_Letter_photo_farmer",credaModel.getConsent_Letter_photo_farmer());
                editor.putString("Consent_Letter_photo",credaModel.getConsent_Letter_photo());
                editor.putString("bor_clean_status",credaModel.getBor_clean_status());
                editor.putString("customer_satify_status",credaModel.getCustomer_satify_status());
                editor.putString("power_connection_status",credaModel.getPower_connection_status());
                editor.putString("pump_head",credaModel.getPump_head());



                    editor.commit();
                    mCtx.startActivity(i);
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = credamodellist;
                } else {
                    List<CredaModel> filteredList = new ArrayList<>();
                    for (CredaModel row : credamodellist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBenifname().toLowerCase().contains(charString.toLowerCase()) || row.getContact().contains(charSequence)
                                || row.getRegistrationno().contains(charSequence) || row.getDistrict().contains(charSequence)
                                || row.getMedaReg().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<CredaModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.item) {
            Toast.makeText(v.getContext(), "ITEM PRESSED = ", Toast.LENGTH_SHORT).show();


        }

    }


    class Benifviewholder extends RecyclerView.ViewHolder {

        TextView OLD_RMU, Benifname, Regnnumber, Fname,meda_reg, contactno, village,
                pumptype, district, block,sceme;
        RelativeLayout Next;
        ImageView upload,download;
        LinearLayout benifIdLayout;

        public Benifviewholder(View itemView) {
            super(itemView);

            Benifname = itemView.findViewById(R.id.benifnameid);
            OLD_RMU = itemView.findViewById(R.id.old_rmu_ide);
            Regnnumber = itemView.findViewById(R.id.numberid);
            Fname = itemView.findViewById(R.id.fatherid);
            contactno = itemView.findViewById(R.id.contactid);
            village = itemView.findViewById(R.id.villageid);
            pumptype = itemView.findViewById(R.id.pumpid);
            district = itemView.findViewById(R.id.districtid);
            block = itemView.findViewById(R.id.blockid);
            Next = itemView.findViewById(R.id.item);
            sceme=itemView.findViewById(R.id.sceme);
            upload = itemView.findViewById(R.id.upload);
            download=itemView.findViewById(R.id.download);
            meda_reg=itemView.findViewById(R.id.meda_reg);
            benifIdLayout = itemView.findViewById(R.id.benifId);

        }

    }

}
