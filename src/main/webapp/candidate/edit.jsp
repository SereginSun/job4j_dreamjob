<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.job4j.dreamjob.models.Candidate" %>
<%@ page import="ru.job4j.dreamjob.store.PsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/dreamjob/loadCities',
                dataType: 'json'
            }).done(function (data) {
                for (var city of data) {
                    $('#cities').append(`<option value="${city.id}">${city.name}</option>`)
                }
            }).fail(function (err) {
                console.log(err);
            });
        });

        function validate() {
            let name = $('#name').val();
            let city = $('#cities').val();
            if (name === '') {
                alert($('#name').attr('title'));
                return false;
            }
            if (city === '0') {
                alert($('#cities').attr('title'));
                return false;
            }
            return true;
        }
    </script>

    <title>???????????? ??????????</title>

</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "");
    if (id != null) {
        candidate = PsqlStore.instOf().findCandidateById(Integer.parseInt(id));
    }
%>
<div class="container pt-3">
    <jsp:include page="/header.jsp"/>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                ?????????? ????????????????.
                <% } else { %>
                ???????????????????????????? ????????????????.
                <% } %>
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>" method="post">
                    <div class="form-group">
                        <label>??????</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>"
                               id="name" title="?????????????? ??????" placeholder="?????????????? ?????? ?? ??????????????"/>
                    </div>
                    <div class="form-group">
                        <label for="cities">??????????</label>
                        <select class="form-control" id="cities" name="city" title="???????????????? ??????????">
                            <option selected value="0">???????????????? ??????????...</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validate()">??????????????????</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
