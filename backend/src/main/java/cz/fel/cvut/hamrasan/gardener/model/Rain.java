package cz.fel.cvut.hamrasan.gardener.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "APP_RAIN")
@NamedQueries({
        @NamedQuery(name = "Rain.findLatest", query = "SELECT r FROM Rain r WHERE r.deleted_at is null ORDER BY r.date DESC ")
})
public class Rain extends AbstractEntity{


        @Basic(optional = false)
        @Column(nullable = false)
        private LocalDateTime date;

        @Basic(optional = false)
        @Column(nullable = false)
        private boolean raining;

        @ManyToOne
        @JoinColumn(name = "garden_id", nullable = false)
        private Garden garden;


        public Rain(LocalDateTime date, boolean raining, Garden garden) {

            this.date = date;
            this.raining = raining;
            this.garden = garden;
        }


        public Rain() {

        }


        public LocalDateTime getDate() {

            return date;
        }


        public void setDate(LocalDateTime localDate) {

            this.date = localDate;
        }


        public boolean getRaining() {

            return raining;
        }


        public void setRaining(boolean raining) {

            this.raining = raining;
        }


        public Garden getGarden() {

            return garden;
        }


        public void setGarden(Garden garden) {

            this.garden = garden;
        }

}
