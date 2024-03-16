$(document).ready(
    function() {

        // GET REQUEST
        $("#getALlPassages").click(function(event) {
            event.preventDefault();
            ajaxGet();
        });

        // DO GET
        function ajaxGet() {
            $.ajax({
                type : "GET",
                url : "getPassages",
                success : function(result) {
                    if (result.status === "success") {
                        $('#getResultDiv').empty();

                        $.each(result.data,
                            function(i, passage) {
                                var dateObj = new Date(passage.date);
                                // Extraire la partie date
                                var year = dateObj.getFullYear();
                                var month = dateObj.getMonth() + 1; // Janvier = 0
                                var day = dateObj.getDate();
                                // Formater la date
                                var formattedDate = day + "/" + month + "/" + year;

                                console.log("Success: ", passage);
                                var tableRowHtml = "<tr>"
                                    + "<td>"+ formattedDate +"</td>"
                                    + "<td>"+ passage.name+"</td>"
                                    + "<td>"+ passage.amountPlus+"</td>"
                                    + "<td>"+ passage.amountMinus+"</td>"
                                    +"</tr>";
                                $('#getResultDiv').append(tableRowHtml);
                            });

                    } else {
                        $("#getResultDiv").html("<strong>Error</strong>");
                        console.log("Fail: ", result.status);
                    }
                },
                error : function(e) {
                    $("#getResultDiv").html("<strong>Error</strong>");
                    console.log("ERROR: ", e);
                }
            });
        }
    }
)