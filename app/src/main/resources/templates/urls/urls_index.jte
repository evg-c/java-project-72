@import gg.jte.Content
@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th>Имя</th>
                        <th class="col-2">Последняя проверка</th>
                        <th class="col-1">Код ответа</th>
                    </tr>
                </thead>
                <tbody>
                    @for(var urls: page.getUrls())
                            <tr>
                                <td>${urls.getUrl().getId()}</td>
                                <td><a href="urls/${urls.getUrl().getId()}">${urls.getUrl().getName()}</a></td>
                                <td>${urls.getLastDate()}</td>
                                <td>${urls.getStatusCode()}</td>
                            </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </section>
    `,
    footer = @`
        <footer class="footer border-top py-3 mt-5 bg-light">
            <div class="container-xl">
                <div class="text-center">
                    created by
                    <a href="https://evg-c.github.io" target="_blank">Evg</a>
                </div>
            </div>
        </footer>
    `
)

