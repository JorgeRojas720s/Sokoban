/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jitor
 */
@Entity
@Table(name = "TBL_GAMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g"),
    @NamedQuery(name = "Game.findByGamId", query = "SELECT g FROM Game g WHERE g.gamId = :gamId"),
    @NamedQuery(name = "Game.findByGamPlayername", query = "SELECT g FROM Game g WHERE g.gamPlayername = :gamPlayername"),
    @NamedQuery(name = "Game.findByGamGame", query = "SELECT g FROM Game g WHERE g.gamGame = :gamGame"),
    @NamedQuery(name = "Game.findByGamLevel", query = "SELECT g FROM Game g WHERE g.gamLevel = :gamLevel"),
    @NamedQuery(name = "Game.findByGamSteps", query = "SELECT g FROM Game g WHERE g.gamSteps = :gamSteps")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GAM_ID")
    private Short gamId;
    @Column(name = "GAM_PLAYERNAME")
    private String gamPlayername;
    @Column(name = "GAM_GAME")
    private String gamGame;
    @Column(name = "GAM_LEVEL")
    private Short gamLevel;
    @Column(name = "GAM_STEPS")
    private Short gamSteps;

    public Game() {
    }
    
    public Game(Short pId,String pName, Short pLevel, Short pSteps) {
        
        this.gamId = pId;
        this.gamPlayername = pName;
        this.gamLevel = pLevel;
        this.gamSteps = pSteps;
        
    }

    public Game(Short gamId) {
        this.gamId = gamId;
    }

    public Short getGamId() {
        return gamId;
    }

    public void setGamId(Short gamId) {
        this.gamId = gamId;
    }

    public String getGamPlayername() {
        return gamPlayername;
    }

    public void setGamPlayername(String gamPlayername) {
        this.gamPlayername = gamPlayername;
    }

    public String getGamGame() {
        return gamGame;
    }

    public void setGamGame(String gamGame) {
        this.gamGame = gamGame;
    }

    public Short getGamLevel() {
        return gamLevel;
    }

    public void setGamLevel(Short gamLevel) {
        this.gamLevel = gamLevel;
    }

    public Short getGamSteps() {
        return gamSteps;
    }

    public void setGamSteps(Short gamSteps) {
        this.gamSteps = gamSteps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gamId != null ? gamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gamId == null && other.gamId != null) || (this.gamId != null && !this.gamId.equals(other.gamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Game[ gamId=" + gamId + " ]";
    }
    
}
