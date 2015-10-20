function validate() {
            var firstname = document.getElementById('first_name').value;
            var lastname = document.getElementById('last_name').value;
            var email = document.getElementById('email').value;
            var filter = /[\w-]+@([\w-]+\.)+[\w-]+/;
            var four_digits = /^[0-9]{4}$/;
            var two_digits = /^[0-9]{2}$/;

            if (firstname == null || firstname == "" || lastname == null || lastname == "") {
                alert('Please enter a valid name');
                return false;
            } else if (document.getElementById('zip') == "" || document.getElementById('state') == "" || document.getElementById('city') == "" || document.getElementById('address_1') == "") {
                alert('Please enter a valid address.');
                return false;
            } else if (!filter.test(email)) {
                alert('Please enter a valid email address');
                return false;
            } else if (!two_digits.test(document.getElementById('area').value) || !four_digits.test(document.getElementById('numb_first').value) || !four_digits.test(document.getElementById('numb_second').value)) {
                alert('Please enter a valid phone number');
                return false;
            }
            if (!two_digits.test(document.getElementById('month').value) || !two_digits.test(document.getElementById('day').value) || !four_digits.test.getElementById('year').value) {
                alert('Please enter a valid day of birth');
                return false;
            }
            return true;
        }

        function valid(form) {
            document.order_form.total.value = '$' + ((document.order_form.ap.value * 21.60)
            + (document.order_form.smf.value * 142.86)
            + (document.order_form.od.value * 36.42)
            + (document.order_form.hd.value * 135.34)
            + (document.order_form.pcf.value * 23.35)).toFixed(2);
        }