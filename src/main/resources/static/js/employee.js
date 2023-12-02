$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            type: 'GET',
            success: function (employee, status) {
                $('#idEdit').val(employee.id);
                $('#fullnameEdit').val(employee.name);
                $('#phoneEdit').val(employee.phoneNumber);
                $('#addressEdit').val(employee.address);
                $('#emailEdit').val(employee.email);
                 $('#salaryEdit').val(employee.email);
                $('#editModal').modal();
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

$('document').ready(function (){
    $('table #deleteButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (category, status){
            $('#idDelete').val(category.id);
        });
        $('#deleteModal').modal();
    });
});

function saveCustomer(event) {
  event.preventDefault();

  var name = document.getElementById("nameAdd").value;
  var address = document.getElementById("addressAdd").value;
  var phone = document.getElementById("phoneAdd").value;
  var email = document.getElementById("emailAdd").value;
  var salary = document.getElementById("salaryAdd").value;

  var phoneRegex = /^\d{10}$/;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  var nameRegex = /^[a-zA-Z\s]+$/;
  if (name === "" || phone === "" || address === "" || email === "" || salary === "") {
    alert("Vui lòng điền đầy đủ thông tin");
    return;
  }

if (!nameRegex.test(name)) {
    alert("Tên không hợp lệ Vui lòng nhập tên khác" );
     return;
  }
  if (!phoneRegex.test(phone)) {
    alert("Số điện thoại không hợp lệ Vui Lòng nhập số khác");
     return;
  }

  if(!emailRegex.test(email)) {
    alert("Định dạng email không đúng vui lòng nhập định dạng khác");
     return;
   }
  document.getElementById("customerForm").submit();
}

function saveCustomerEdit(event) {
  event.preventDefault();

  var name = document.getElementById("fullnameEdit").value;
  var address = document.getElementById("addressEdit").value;
  var phone = document.getElementById("phoneEdit").value;
  var email = document.getElementById("emailEdit").value;
  var salary = document.getElementById("salaryEdit").value;

  var phoneRegex = /^\d{10}$/;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  var nameRegex = /^[a-zA-Z\s]+$/;
  if (name === "" || phone === "" || address === "" || email === "") {
    alert("Vui lòng điền đầy đủ thông tin");
    return;
  }

if (!nameRegex.test(name)) {
    alert("Tên không hợp lệ Vui lòng nhập tên khác" );
     return;
  }
  if (!phoneRegex.test(phone)) {
    alert("Số điện thoại không hợp lệ Vui Lòng nhập số khác");
     return;
  }

  if(!emailRegex.test(email)) {
    alert("Định dạng email không đúng vui lòng nhập định dạng khác");
     return;
   }
  document.getElementById("editForm").submit();
}