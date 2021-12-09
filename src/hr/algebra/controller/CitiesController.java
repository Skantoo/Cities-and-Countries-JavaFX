/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Country;
import hr.algebra.viewcontroller.CityViewModel;
import hr.algebra.viewcontroller.CountryViewModel;
import hr.algerba.utilities.FileUtils;
import hr.algerba.utilities.ImageUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author Bruno
 */
public class CitiesController implements Initializable {

    private Map<Control, Label> validationMapCity;
    
    private Map<TextField, Label> validationMapCountry;
    
    private final ObservableList<CountryViewModel> countries = FXCollections.observableArrayList();
    
    private final ObservableList<CityViewModel> cities = FXCollections.observableArrayList();
   
    private CountryViewModel selectedCountryViewModel;
    
    private CityViewModel selectedCityViewModel;
    
    private ObservableList<Country> C = FXCollections.observableArrayList();
    
    @FXML
    private TabPane tpContent;
    @FXML
    private Tab tabAddCity;
    @FXML
    private Tab tabAddCountry;
    @FXML
    private TableColumn<CountryViewModel, String> tcCountryName;
    @FXML
    private TableColumn<CountryViewModel, Integer> tcCountryID;
    @FXML
    private TableColumn<CityViewModel, String> tcCityName;
    @FXML
    private TableColumn<CityViewModel, Integer> tcCityCountryID;
    @FXML
    private TextField tfCountryName;
    @FXML
    private ComboBox<Country> cbCountryID;
    @FXML
    private TextField tfCityName;
    @FXML
    private Button btnUploadImageCity;
    @FXML
    private Button btnCommitCity;
    @FXML
    private Tab tabCountries;
    @FXML
    private TableView<CountryViewModel> tvCountry;
    @FXML
    private Tab tabCities;
    @FXML
    private TableView<CityViewModel> tvCities;
    @FXML
    private Button btnUploadCountry;
    @FXML
    private Button btnCommitCountry;
    @FXML
    private ImageView ivCountry;
    @FXML
    private ImageView ivCity;
    @FXML
    private Label lblCountryError, lblCountryPictureError, lblCityNameError, lblCountryIDError, lblCityPictureError;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initValidationCity();
        initValidationCountry();
        initCountries();
        initCities();
        initComboBox();
        initTable();
        setupListenersCountry();
        setupListenersCity();
    }    

    @FXML
    private void editCountry(ActionEvent event) {
        if (tvCountry.getSelectionModel().getSelectedItem() != null) {
            bindCountry(tvCountry.getSelectionModel().getSelectedItem());
            tpContent.getSelectionModel().select(tabAddCountry);            
        }
    }

    @FXML
    private void deleteCountry(ActionEvent event) {
        if (tvCountry.getSelectionModel().getSelectedItem() != null) {
            countries.remove(tvCountry.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void editCity(ActionEvent event) {
        if (tvCities.getSelectionModel().getSelectedItem() != null) {
            bindCity(tvCities.getSelectionModel().getSelectedItem());
            tpContent.getSelectionModel().select(tabAddCity);            
        }
    }

    @FXML
    private void deleteCity(ActionEvent event) {
        if (tvCities.getSelectionModel().getSelectedItem() != null) {
            cities.remove(tvCities.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void commitCountry(ActionEvent event) {
         if (formValidCountry()) {
            selectedCountryViewModel.getCountry().setImeDrzave(tfCountryName.getText().trim());
            if (selectedCountryViewModel.getCountry().getIDCountry()== 0) {
                countries.add(selectedCountryViewModel);
            } else {
                try {
                    // we cannot listen to the upates
                    RepositoryFactory.getRepository().updateCountry(selectedCountryViewModel.getCountry());
                    tvCountry.refresh();
                } catch (Exception ex) {
                    Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            selectedCountryViewModel = null;
            tpContent.getSelectionModel().select(tabCountries);
            resetFormCountry();
        }
    }


    @FXML
    private void commitCity(ActionEvent event) {
        
        
        if (formValidCity()) {
            selectedCityViewModel.getCity().setImeGrada(tfCityName.getText().trim());
            selectedCityViewModel.getCity().setCountryID(Integer.valueOf(((Country)cbCountryID.getValue()).getIDCountry()));
            if (selectedCityViewModel.getCity().getIDCity()== 0) {
                cities.add(selectedCityViewModel);
            } else {
                try {
                    // we cannot listen to the upates
                    RepositoryFactory.getRepository().updateCity(selectedCityViewModel.getCity());
                    tvCities.refresh();
                } catch (Exception ex) {
                    Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            selectedCityViewModel = null;
            tpContent.getSelectionModel().select(tabCities);
            resetFormCity();
        }

    }
    
    private void initTable(){
        tcCountryName.setCellValueFactory(country -> country.getValue().getImeDrzaveProperty());
        tcCountryID.setCellValueFactory(country -> country.getValue().getIDCountryProperty().asObject());
        tvCountry.setItems(countries);
        tcCityName.setCellValueFactory(city -> city.getValue().getImeGradaProperty());
        tcCityCountryID.setCellValueFactory(city-> city.getValue().getCountryIDProperty().asObject());
        tvCities.setItems(cities);
        
    }

    private void initCountries() {
        try {
            RepositoryFactory.getRepository().getCountries().forEach(country-> countries.add(new CountryViewModel(country)));
        } catch (Exception ex) {
            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE,null,ex);
            new Alert(Alert.AlertType.ERROR,"Unable to load the form. Exiting...").show();
        }
    }

    private void initCities() {
        try {
            RepositoryFactory.getRepository().getCities().forEach(city-> cities.add(new CityViewModel(city)));
        } catch (Exception ex) {
            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE,null,ex);
            new Alert(Alert.AlertType.ERROR,"Unable to load the form. Exiting...").show();
        }
    }

    private void setupListenersCountry() {
        tpContent.setOnMouseClicked(event -> {
            if (tpContent.getSelectionModel().getSelectedItem().equals(tabAddCountry)) {
                bindCountry(null);
            }
        });
        countries.addListener((ListChangeListener.Change<? extends CountryViewModel> change) -> {
            if (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(pvm -> {
                        try {
                            RepositoryFactory.getRepository().deleteCountry(pvm.getCountry());
                        } catch (Exception ex) {
                            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                } else if (change.wasAdded()) {
                    change.getAddedSubList().forEach(pvm -> {
                        try {
                            int idCountry = RepositoryFactory.getRepository().addCountry(pvm.getCountry());
                            pvm.getCountry().setIDCountry(idCountry);
                        } catch (Exception ex) {
                            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        });
    }

    private void setupListenersCity() {
        tpContent.setOnMouseClicked(event -> {
            if (tpContent.getSelectionModel().getSelectedItem().equals(tabAddCity)) {
                bindCountry(null);
            }
        });
        cities.addListener((ListChangeListener.Change<? extends CityViewModel> change) -> {
            if (change.next()) {
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(pvm -> {
                        try {
                            RepositoryFactory.getRepository().deleteCity(pvm.getCity());
                        } catch (Exception ex) {
                            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                } else if (change.wasAdded()) {
                    change.getAddedSubList().forEach(pvm -> {
                        try {
                            int idCity = RepositoryFactory.getRepository().addCity(pvm.getCity());
                            pvm.getCity().setIDCity(idCity);
                        } catch (Exception ex) {
                            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                }
            }
        });
    }

    private void bindCountry(CountryViewModel viewModel) {
        resetFormCountry();
        
        selectedCountryViewModel= viewModel != null ? viewModel : new CountryViewModel(null);
        tfCountryName.setText(selectedCountryViewModel.getImeDrzaveProperty().get());
        ivCountry.setImage(selectedCountryViewModel.getPictureProperty().get() != null ? new Image(new ByteArrayInputStream(selectedCountryViewModel.getPictureProperty().get())) : new Image(new File("src/assets/no_image.png").toURI().toString()));
    }

    private void bindCity(CityViewModel viewModel) {
        try {
            resetFormCity();
            
            selectedCityViewModel= viewModel != null ? viewModel : new CityViewModel(null);
            tfCityName.setText(selectedCityViewModel.getImeGradaProperty().get());
            ivCity.setImage(selectedCityViewModel.getPictureProperty().get() != null ? new Image(new ByteArrayInputStream(selectedCityViewModel.getPictureProperty().get())) : new Image(new File("src/assets/no_image.png").toURI().toString()));
            cbCountryID.getSelectionModel().select(RepositoryFactory.getRepository().getCountry(selectedCityViewModel.getCountryIDProperty().get()));
           
        } catch (Exception ex) {
            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetFormCity() {
        validationMapCity.values().forEach(lbl -> lbl.setVisible(false));
    }
    
    private void resetFormCountry() {
        validationMapCountry.values().forEach(lbl -> lbl.setVisible(false));
    }

    @FXML
    private void uploadCountryImg(ActionEvent event) throws IOException {
        File file = FileUtils.uploadFileDialog(tfCountryName.getScene().getWindow(), "jpg", "jpeg", "png");
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            selectedCountryViewModel.getCountry().setPicture(ImageUtils.imageToByteArray(image, ext));
            ivCountry.setImage(image);
        }
    }

    @FXML
    private void uploadCityImg(ActionEvent event) throws IOException {
        File file = FileUtils.uploadFileDialog(tfCityName.getScene().getWindow(), "jpg", "jpeg", "png");
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            selectedCityViewModel.getCity().setPicture(ImageUtils.imageToByteArray(image, ext));
            ivCity.setImage(image);
        }
    }

    private boolean formValidCity() {
        final AtomicBoolean ok = new AtomicBoolean(true);
        validationMapCity.keySet().forEach(tf -> {
            if (tfCityName.getText().trim().isEmpty()) {
                validationMapCity.get(tf).setVisible(true);
                ok.set(false);
            } else {
                validationMapCity.get(tf).setVisible(false);
            }
        });

        if (selectedCityViewModel.getPictureProperty().get() == null) {
            lblCityPictureError.setVisible(true);
            ok.set(false);
        } else {
            lblCityPictureError.setVisible(false);
        }
        return ok.get();
    }

    private boolean formValidCountry() {
        final AtomicBoolean ok = new AtomicBoolean(true);
        validationMapCountry.keySet().forEach(tf -> {
            if (tfCountryName.getText().trim().isEmpty() ) {
                validationMapCountry.get(tf).setVisible(true);
                ok.set(false);
            } else {
                validationMapCountry.get(tf).setVisible(false);
            }
        });

        if (selectedCountryViewModel.getPictureProperty().get() == null) {
            lblCountryPictureError.setVisible(true);
            ok.set(false);
        } else {
            lblCountryPictureError.setVisible(false);
        }
        return ok.get();  
    }

    @FXML
    private void select(ActionEvent event) {
    }

    private void initValidationCountry() {
        validationMapCountry = Stream.of(
                new SimpleImmutableEntry<>(tfCountryName, lblCountryError)     
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    private void initValidationCity() {
        validationMapCity = Stream.of(
                new SimpleImmutableEntry<>(tfCityName, lblCityNameError),
                new SimpleImmutableEntry<>(cbCountryID, lblCountryIDError)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void initComboBox() {
        try {
            RepositoryFactory.getRepository().getCountries().forEach(A->C.add(A));
            cbCountryID.setItems(C);
        } catch (Exception ex) {
            Logger.getLogger(CitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
