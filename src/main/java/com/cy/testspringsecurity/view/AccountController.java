package com.cy.testspringsecurity.view;

import com.cy.testspringsecurity.domain.Account;
import com.cy.testspringsecurity.repo.AccountRepository;
import com.cy.testspringsecurity.repo.QRCodeTicketRepository;
import com.cy.testspringsecurity.utils.ApplicationContextProvider;
import com.cy.testspringsecurity.utils.PasswordUtils;
import com.cy.testspringsecurity.utils.TOTPUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.util.Messages;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.beans.PropertyEditorSupport;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Slf4j
@ManagedBean
@RequestScoped
public class AccountController {

    private AccountRepository acccountRepository = (AccountRepository) ApplicationContextProvider.getBean("accountRepositoryHashImpl");

    private QRCodeTicketRepository qrCodeTicketRepository = (QRCodeTicketRepository) ApplicationContextProvider.getBean("QRCodeTicketRepositoryHashImpl");

    //    @Value("${qrcode.width}")
    private int qrcodeWidth = 400;

    //    @Value("${qrcode.height}")
    private int qrcodeHeight = 400;

    //    @Value("${totp.hostLabel}")
    private String hostLabel = "cy.com";

    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String code;

    @Getter
    private String renderMethod = "canvas'";
    @Getter
    private String text = "";
    @Getter
    private int mode = 2;
    @Getter
    private int size = 200;
    @Getter
    private String fillColor = "7d767d";


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {

        dataBinder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null) {
                    return;
                }
                setValue(text.replaceAll("<.[^<>]*?>", ""));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return (value != null ? value.toString() : "");
            }
        });

    }

    public void create() {
        if (acccountRepository.exist(name)) {
            Messages.addGlobalError("The account already exist");
        }
        Account account = new Account();
        account.setName(name);
        account.setPassword(PasswordUtils.encrypt(password, name));
        account.setSecret(TOTPUtils.generateSecret());
        account.setCreated(new Date());
        acccountRepository.addAccount(account);

        text = getQRBarcodeURL(account.getName(), hostLabel, account.getSecret());
        qrCodeTicketRepository.createTicket(name);
        Messages.addGlobalInfo("Success");
    }

    public void verifyCode(String name, String password, long code) throws InvalidKeyException, NoSuchAlgorithmException {
        Account account = acccountRepository.findAccountByName(name);
        if (account != null && checkPassword(account, password)) {
            Messages.addGlobalInfo("admin verify password Success");
        }
        if (account != null && checkPassword(account, password) && TOTPUtils.checkCode(account.getSecret(), code)) {
            Messages.addGlobalInfo("Success");
        } else {
            Messages.addGlobalError("Verification failed");
        }
    }

    public void showQrcode() throws IllegalAccessException {
        if (!qrCodeTicketRepository.useTicket(name)) {
            throw new IllegalAccessException("no permission");
        }
        Account account = acccountRepository.findAccountByName(name);
        text = getQRBarcodeURL(account.getName(), hostLabel, account.getSecret());
    }

    private boolean checkPassword(Account account, String password) throws NoSuchAlgorithmException, InvalidKeyException {
        return account.getPassword().equals(PasswordUtils.encrypt(password, account.getName()));
    }

    private String getQRBarcodeURL(String user, String host, String secret) {
        String format = "otpauth://totp/%s@%s?secret=%s";
        return String.format(format, user, host, secret);
    }

}
